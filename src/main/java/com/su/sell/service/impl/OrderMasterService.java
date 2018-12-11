package com.su.sell.service.impl;

import com.su.sell.converter.OrderMaster2OrderMasterDTO;
import com.su.sell.dataObject.OrderDetail;
import com.su.sell.dataObject.OrderMaster;
import com.su.sell.dataObject.ProductInfo;
import com.su.sell.dto.CartDto;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.enums.OrderStatusEnum;
import com.su.sell.enums.PayStatusEnum;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.repository.OrderDetailRepository;
import com.su.sell.repository.OrderMasterRepository;
import com.su.sell.repository.ProductInfoRepository;
import com.su.sell.service.IOrderMasterService;
import com.su.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class OrderMasterService implements IOrderMasterService {
    @Autowired
    private OrderMasterRepository masterRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductService productService;


    @Override
    public OrderMasterDTO create(OrderMasterDTO dto) {
        //查询商品(数量,价格)
        List<OrderDetail> orderDetails = dto.getOrderDetails();
        List<String>productIds = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        String orderId = KeyUtil.getUniqueKey();
        for (OrderDetail orderDetail : orderDetails) {
            ProductInfo product = productInfoRepository.findById(orderDetail.getProductId()).get();
            //如果查询的产品有为空的.或者产品库存小于订单 则抛出异常
            if(product==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if(product.getProductStock()<orderDetail.getProductQuantity()){
                throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
            }
            totalAmount = product.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity().toString())).add(totalAmount).setScale(2, RoundingMode.HALF_UP);
            //写入数据库(detail 和 master)
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(product,orderDetail);
            detailRepository.save(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        dto.setOrderId(orderId);
        BeanUtils.copyProperties(dto,orderMaster);
        orderMaster.setOrderAmount(totalAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);
        /** 库存减少. */
         //获取cartDto的list
        List<CartDto> cartDtoList = dto.getOrderDetails().stream().map
            (e -> new CartDto(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreseStock(cartDtoList);
        return dto;
    }

    @Override
    public OrderMasterDTO findOne(String orderId) {
        Optional<OrderMaster> optional = masterRepository.findById(orderId);
        if(!optional.isPresent()){
            throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        OrderMaster orderMaster = optional.get();
        //根据订单主表查到明细
        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);
        if(orderDetailList.size()==0){
            throw  new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderMasterDTO orderDto = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetails(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderMasterDTO> findList(String buyerOpenid, Pageable page) {
        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyerOpenid(buyerOpenid, page);

        List<OrderMasterDTO> OrderDtoList = OrderMaster2OrderMasterDTO.convert(orderMasterPage.getContent());

        return new PageImpl<OrderMasterDTO>(OrderDtoList,page,orderMasterPage.getTotalElements());
    }

    @Override
    public OrderMasterDTO channel(OrderMasterDTO dto) {
        //判断订单状态 如果订单不是新订单,则抛出异常
        String orderId = dto.getOrderId();
        OrderMaster orderMaster = masterRepository.findById(orderId).get();
        if(!(orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))){
            log.error("【取消订单】 订单状态不正确为,orderId=:{},orderStatus=:{}", orderId,orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        if(orderMaster!=null){
            //修改订单状态
            orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
            List<OrderDetail> detailList = detailRepository.findByOrderId(orderId);
            for (OrderDetail orderDetail : detailList) {
                //返回库存
                String productId = orderDetail.getProductId();
                ProductInfo productInfo = productInfoRepository.findById(productId).get();
                Integer productQuantity = orderDetail.getProductQuantity();
                productInfo.setProductStock(productInfo.getProductStock()+productQuantity);
                ProductInfo save = productInfoRepository.save(productInfo);
                if(save==null){
                    log.error("【订单更新失败】,orderId:{}",orderId);
                    throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
                }
            }
            //如果已支付,需要退款
            if(orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
                //TODO
            }
            //将值设置给dto
            return OrderMaster2OrderMasterDTO.convert(orderMaster);
        }
        throw new SellException(ResultEnum.ORDER_NOT_EXIST);


    }

    @Override
    public OrderMasterDTO finish(OrderMasterDTO dto) {
        return null;
    }

    @Override
    public OrderMasterDTO paid(OrderMasterDTO dto) {
        return null;
    }
}

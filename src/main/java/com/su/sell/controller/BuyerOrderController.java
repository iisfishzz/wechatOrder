package com.su.sell.controller;

import com.su.sell.converter.OrderForm2OrderDTO;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.form.OrderForm;
import com.su.sell.service.IOrderMasterService;
import com.su.sell.util.ResultVOUtil;
import com.su.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private IOrderMasterService orderMasterService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】，参数不正确:{}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderMasterDTO dto = OrderForm2OrderDTO.convert(orderForm);
        //调用创建订单方法
        OrderMasterDTO orderMasterDTO = orderMasterService.create(dto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderMasterDTO.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @PostMapping("/list")
    public ResultVO<Map<String,String>> orderList (@RequestParam("openid")String openId,
                                                   @RequestParam(value = "page",defaultValue = "0")Integer page,
                                                   @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openId)){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = new PageRequest(page,size);
        //查出

        Page<OrderMasterDTO> list = orderMasterService.findList(openId, pageable);
        List<OrderMasterDTO> content = list.getContent();
        return ResultVOUtil.success(content);
    }



}

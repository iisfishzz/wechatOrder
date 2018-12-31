package com.su.sell.service.impl;

import com.su.sell.dataObject.ProductInfo;
import com.su.sell.dto.CartDto;
import com.su.sell.enums.ProductStatusEnum;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.repository.ProductInfoRepository;
import com.su.sell.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class ProductService implements IProductService{
    @Autowired
    private ProductInfoRepository repository;


    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDto> cartDtoList) {

        for (CartDto cartDto : cartDtoList) {

        }
    }

    @Override
    public void decreseStock(List<CartDto> cartDtoList) {
        //查看库存是否够,如果够就剪掉,然后更新
        List <ProductInfo> productInfoList = new ArrayList<>();
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findById(cartDto.getProductId()).get();
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int ret = productInfo.getProductStock() - cartDto.getProductQuantity();
            if(ret < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR.getCode(),"商品: "+productInfo.getProductName()+"库存不足,请选择其他商品或减少数量QAQ");
            }
            productInfo.setProductStock(ret);
            productInfoList.add(productInfo);
        }
        repository.saveAll(productInfoList);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo one = findOne(productId);
        if(one==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(one.getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        one.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(one);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo one = findOne(productId);
        if(one==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(one.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        one.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(one);
    }
}

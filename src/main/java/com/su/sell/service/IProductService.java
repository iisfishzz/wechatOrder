package com.su.sell.service;

import com.su.sell.dataObject.ProductInfo;
import com.su.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService  {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
    void increaseStock(List<CartDto> cartDtoList);
    void decreseStock(List<CartDto> cartDtoList);
}

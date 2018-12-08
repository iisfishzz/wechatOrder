package com.su.sell.dto;

import lombok.Data;

/**
 * 购物车类
 * createBy: suxu
 */
@Data
public class CartDto {
    private String productId;
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

package com.su.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * Created by 廖师兄
 * 2017-05-09 17:33
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(1,"上架"),
    DOWN(0,"下架");
    private Integer code;
    private String message;


    ProductStatusEnum(int status, String msg) {
        this.code = status;
        this.message = msg;
    }
}

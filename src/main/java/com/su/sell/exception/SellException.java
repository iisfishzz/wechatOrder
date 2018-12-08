package com.su.sell.exception;

import com.su.sell.enums.ResultEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {
    private Integer code;
    public SellException(ResultEnum result){
        super(result.getMessage());
        this.code = result.getCode();
    }
    public SellException(Integer code,String message){
        super(message);
        this.code =code;
    }
}

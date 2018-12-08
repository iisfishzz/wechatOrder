package com.su.sell.service;

import com.su.sell.dto.CartDto;

import java.util.List;

public interface IProductService  {
    void increaseStock(List<CartDto> cartDtoList);
    void decreseStock(List<CartDto> cartDtoList);
}

package com.su.sell.service;

import com.su.sell.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IOrderMasterService {
    /** 创建订单. */
    OrderMasterDTO create(OrderMasterDTO dto);
    /** 查询单个订单. */
    OrderMasterDTO findOne(String orderId);
    /** 查询订单列表. */
    Page<OrderMasterDTO> findList(String buyerOpenid,Pageable page);
    /** 取消订单. */
    OrderMasterDTO channel(OrderMasterDTO dto);
    /** 完结订单. */
    OrderMasterDTO finish(OrderMasterDTO dto);
    /** 支付订单. */
    OrderMasterDTO paid(OrderMasterDTO dto);

}

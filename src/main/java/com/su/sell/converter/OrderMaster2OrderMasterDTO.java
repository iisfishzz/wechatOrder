package com.su.sell.converter;

import com.su.sell.dataObject.OrderMaster;
import com.su.sell.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderMasterDTO {
    public static OrderMasterDTO convert(OrderMaster orderMaster){
        OrderMasterDTO orderDto = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster,orderDto);

        return orderDto;
    }
    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderMasterDTO> orderMasterDTOList = orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return orderMasterDTOList;
    }
}

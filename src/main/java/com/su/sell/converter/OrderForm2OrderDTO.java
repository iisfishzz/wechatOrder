package com.su.sell.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.su.sell.dataObject.OrderDetail;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
* @Description:    将前端传来的OrderFormat对象转换为OrderMasterDTO
* @Author:         scott
* @CreateDate:     2018/12/8 14:07
* @UpdateUser:     scott
* @UpdateDate:     2018/12/8 14:07
* @UpdateRemark:   修改内容
* @Version:        1.0
*/

@Slf4j
public class OrderForm2OrderDTO {
    public static OrderMasterDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderMasterDTO orderDto = new OrderMasterDTO();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerOpenid(orderForm.getOpenId());
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (JsonSyntaxException e) {
            log.error("对象转换时出错:{},错误信息:{}",orderForm.getItems(),e);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"OrderForm的items对象有问题");
        }
        orderDto.setOrderDetails(orderDetailList);
        return orderDto;
    }
    public static List<OrderMasterDTO> convert(List<OrderForm> orderMasterList){
        List<OrderMasterDTO> orderMasterDTOList = orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return orderMasterDTOList;
    }
}

package com.su.sell.controller;

import com.su.sell.converter.OrderForm2OrderDTO;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.form.OrderForm;
import com.su.sell.service.IOrderMasterService;
import com.su.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private IOrderMasterService orderMasterService;
    //创建订单
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultVO  createOrder(@Valid OrderForm orderForm , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】，参数不正确:{}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderMasterDTO dto = OrderForm2OrderDTO.convert(orderForm);
        orderMasterService.create(dto);
        return new ResultVO();
    }
    //订单列表
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void orderView(){

    }
    //订单详情
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void orderDetail(){

    }
    //取消订单
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void chanelOrder(){

    }


}

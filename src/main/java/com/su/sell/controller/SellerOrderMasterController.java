package com.su.sell.controller;

import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.service.impl.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size, HashMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderMasterDTO> orderDTOPage = orderMasterService.findAll(request);
        map.put("page",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }

    @GetMapping("/channel")
    public ModelAndView channel(@RequestParam("orderId")String orderId,
                                Map<String,Object> map){
        try {
            OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
            orderMasterService.channel(orderMasterDTO);
        }catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.error("【卖家端-取消订单】查询不到订单");
        }
         map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
         map.put("url", "/sell/seller/order/list");
         return new ModelAndView("common/success");
    }
   @GetMapping("/detail")
    public ModelAndView detail(String orderId,Map<String,Object> map){
        OrderMasterDTO orderDTO = null;
        try{
             orderDTO = orderMasterService.findOne(orderId);
        }catch(SellException e){
             log.error("【卖家端查询订单详情】发生异常{}",e);
             map.put("msg",e.getMessage());
             map.put("url","/sell/seller/order/list");
             return new ModelAndView("common/error",map);
        }
         map.put("orderDTO", orderDTO);
         return new ModelAndView("order/detail", map);
   }
   @GetMapping("/finish")
    public ModelAndView finished(String orderId,Map<String,Object>map){
       OrderMasterDTO dto = null;
        try{
            dto = orderMasterService.findOne(orderId);
            orderMasterService.finish(dto);
        }catch (SellException e){
            log.error("【卖家端完结订单】发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
      map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
      map.put("url", "/sell/seller/order/list");
      return new ModelAndView("common/success");
   }


}

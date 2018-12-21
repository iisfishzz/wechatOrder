package com.su.sell.controller;

import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.service.impl.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/seller/order")
public class OrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size, HashMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderMasterDTO> orderDTOPage = orderMasterService.findAll(request);
        map.put("page",orderDTOPage);
        return new ModelAndView("orderList",map);
    }
}

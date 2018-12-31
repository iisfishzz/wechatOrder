package com.su.sell.controller;

import com.su.sell.dataObject.ProductInfo;
import com.su.sell.dto.OrderMasterDTO;
import com.su.sell.exception.SellException;
import com.su.sell.service.impl.ProductService;
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

/**
* @Description:    卖家端商品
* @Author:         scott
* @CreateDate:     2018/12/24 10:56
* @UpdateUser:     scott
* @UpdateDate:     2018/12/24 10:56
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {
    @Autowired
    protected ProductService productService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size, HashMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> orderDTOPage = productService.findAll(request);
        map.put("page",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }
    @RequestMapping("/on_sale")
    public ModelAndView onSell(@RequestParam("productId")String productId,Map<String,Object> map){
        try {
            ProductInfo productInfo = productService.onSale(productId);
        } catch (SellException e) {
            log.error("【商品上架】出现异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    @RequestMapping("/index")
    public void index(String productId,Map<String,Object> map){

    }
}

package com.su.sell.controller;

import com.su.sell.config.ProjectUrlConfig;
import com.su.sell.config.WechatAccountConfig;
import com.su.sell.constans.CookieConstant;
import com.su.sell.constans.RedisConstant;
import com.su.sell.dataObject.SellerInfo;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import com.su.sell.service.SellerService;
import com.su.sell.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @RequestMapping("login")
    public ModelAndView login(@RequestParam("openid") String openId,HttpServletResponse response,
                                Map<String,Object> map){
        //找到openid对应的seller
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openId);
        if(sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL);
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        //将信息存入redis
        String token = UUID.randomUUID().toString();

        Integer expire = RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(RedisConstant.TOKEN_PROFIX+token,openId,expire, TimeUnit.SECONDS);
        //将信息存入cookie
        CookieUtil.setCookie(response,CookieConstant.TOKEN,token,expire);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell()+"/sell/seller/order/list");
    }
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,Map<String,Object> map){
        //从cookie里查询
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if(cookie!=null){
            //清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(RedisConstant.TOKEN_PROFIX+cookie.getValue());
            //清除cookie(覆盖cookie)
            CookieUtil.setCookie(response,CookieConstant.TOKEN,null,0);

        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }


}

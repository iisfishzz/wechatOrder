package com.su.sell.controller;

import com.su.sell.config.WechatAccountConfig;
import com.su.sell.config.WechatMpConfig;
import com.su.sell.enums.ResultEnum;
import com.su.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
* @Description:    使用API的方式完成
* @Author:         scott
* @CreateDate:     2018/12/10 16:19
* @UpdateUser:     scott
* @UpdateDate:     2018/12/10 16:19
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig accountConfig;
    @GetMapping("/authorize")
    public String getUrl(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        //1.配置
        //2.调用方法
        String url = accountConfig.getContent_url();
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("获取code回调地址:{}",redirectUrl);
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code ,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOauth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOauth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】 {}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOauth2AccessToken.getOpenId();
        log.info("获取微信openid:{}",openId);
        log.info("回调地址:{}",returnUrl +"openid="+openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}

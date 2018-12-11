package com.su.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
/**
* @Description:    使用手动方式获取用户资料
* @Author:         scott
* @CreateDate:     2018/12/10 16:19
* @UpdateUser:     scott
* @UpdateDate:     2018/12/10 16:19
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
   /* @Value("${context_url}")
    private String contextUrl;
    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;

    @GetMapping("getCodeUrl")
    public void getCodeUrl(String scope,HttpServletResponse response) throws IOException {
        String encodeUrl = URLEncoder.encode(contextUrl, "UTF-8");
        log.info("重定向URL={}",contextUrl);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+encodeUrl+"&response_type=code&scope="+scope+"#wechat_redirect";
        response.sendRedirect(url);
    }
   @GetMapping("/auth")
    public void auth(@RequestParam("code")String code){
       log.info("进入auth方法...");
       log.info("code = {}",code);
       String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
       RestTemplate restTemplate = new RestTemplate();
       String resp = restTemplate.getForObject(url, String.class);
       System.out.println(resp);
       log.info("使用code访问返回结果: {}",resp);
   }*/

    @RequestMapping(value = "/verify")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        log.info("微信接入服务器");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String token = "weixin";
        String echostr = request.getParameter("echostr");
            log.info("echostr为:{}", echostr);
            if (echostr != null) {
                try {
                    response.getWriter().write(echostr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
   }
}

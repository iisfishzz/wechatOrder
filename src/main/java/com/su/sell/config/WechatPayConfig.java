package com.su.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
* @Description:    微信支付类
* @Author:         scott
* @CreateDate:     2018/12/17 21:56
* @UpdateUser:     scott
* @UpdateDate:     2018/12/17 21:56
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountConfig accountConfig;
    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(accountConfig.getAppId());
        wxPayH5Config.setAppSecret(accountConfig.getSecret());
        wxPayH5Config.setMchId(accountConfig.getMchId());
        wxPayH5Config.setMchKey(accountConfig.getMerKey());
        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }

}

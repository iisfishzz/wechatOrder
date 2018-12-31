package com.su.sell.handler;

import com.su.sell.config.ProjectUrlConfig;
import com.su.sell.exception.SellException;
import com.su.sell.exception.SellerAuthorizeException;
import com.su.sell.util.ResultVOUtil;
import com.su.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
* @Description:    权限异常处理
* @Author:         scott
* @CreateDate:     2018/12/28 23:05
* @UpdateUser:     scott
* @UpdateDate:     2018/12/28 23:05
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@ControllerAdvice
public class SellExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));
    }
    @ExceptionHandler(value = SellException.class)
    //@ResponseStatus(HttpStatus.FORBIDDEN)  要求返回状态不为200时可以在全局异常处理这里设置返回异常状态
    @ResponseBody
    public ResultVO handlerSellException(SellException exception){
        return ResultVOUtil.error(exception.getCode(),exception.getMessage());
    }
}

package com.su.sell.aspect;

import com.su.sell.constans.CookieConstant;
import com.su.sell.constans.RedisConstant;
import com.su.sell.exception.SellerAuthorizeException;
import com.su.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
* @Description:    登录拦截器
* @Author:         scott
* @CreateDate:     2018/12/28 22:55
* @UpdateUser:     scott
* @UpdateDate:     2018/12/28 22:55
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Aspect
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 切面规则
     */
    @Pointcut("execution(public * com.su.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.su.sell.controller.SellerUserController.*(..))")
    public void verify() {}

    /**
     * 在切到的方法执行前执行
     */
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if(cookie==null){
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //查询redis中的token
        String token = stringRedisTemplate.opsForValue().get(RedisConstant.TOKEN_PROFIX + cookie.getValue());
        if (StringUtils.isEmpty(token)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}

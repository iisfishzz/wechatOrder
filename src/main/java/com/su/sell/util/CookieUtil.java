package com.su.sell.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    /**
     *s设置cookie
     * @param resp 回调对象
     * @param name cookie名
     * @param value cookie值
     * @param expire 过期时间(单位: 秒)
     */
    public static  void setCookie(HttpServletResponse resp,
                                    String name, String value,
                                    Integer expire){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(expire);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
    public static Cookie getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)){
                    return cookie;
                }
            }
        return null;
    }
}

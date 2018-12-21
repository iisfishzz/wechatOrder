package com.su.sell.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 将对象转换为json格式
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}

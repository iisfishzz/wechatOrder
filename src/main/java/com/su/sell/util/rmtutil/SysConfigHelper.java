package com.su.sell.util.rmtutil;

import java.util.Properties;

public class SysConfigHelper {

    private static Properties properties;

    public static void setProperties(Properties properties) {
        SysConfigHelper.properties = properties;
    }

    /**
     * 获取通知时间配置
     * @return
     */
    public static String getNotifyTimes(){
        return getConfig("support.notify.time");
    }

    /**
     * 获取服务名称
     * @return
     */
    public static String getServerName(){
        return getConfig("serverInfo.name");
    }

    public static String getConfig(String key){
        if(properties == null){
            throw new RuntimeException("配置项不存在");
        }
        return properties.getProperty(key);
    }

}
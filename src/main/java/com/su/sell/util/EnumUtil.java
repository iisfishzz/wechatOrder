package com.su.sell.util;

import com.su.sell.enums.CodeEnum;

/**
* @Description:    通过枚举的编码(code)获取描述(message)
* @Author:         scott
* @CreateDate:     2018/12/22 10:32
* @UpdateUser:     scott
* @UpdateDate:     2018/12/22 10:32
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class EnumUtil {
    public static <T extends CodeEnum> T getEnumByCode(Integer code,Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()) {
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return  null;
    }
}

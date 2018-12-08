package com.su.sell.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KeyUtil {
    public static synchronized String  getUniqueKey(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String format = sdf.format(new Date());
        Random random = new Random();
        int i = random.nextInt(900000) + 100000;
        return format+i;

    }
}

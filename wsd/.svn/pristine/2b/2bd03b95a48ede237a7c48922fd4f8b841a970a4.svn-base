package com.qzsoft.tah.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtiles {

    /**
     * 计算几分钟之后的时间
     * @param date 时间
     * @param x 多少分钟之后
     * @return
     */
    public static Date addDateMinut(Date date, int x){
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);// 24小时制
        date = cal.getTime();
        return date;
    }

}

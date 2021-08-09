package com.qzsoft.tah.service;

import cn.hutool.http.HttpRequest;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
  * @Author Yang Chunhai
  * @Description  缓存节假日
  * @Date 2021/4/21 17:11
  **/
public class HolidayManager {

    public static final Integer WORK_DAY = 0; // 工作日
    public static final Integer REST_DAY = 1; // 节日
    public static final Integer HOLIDAY = 2; // 假日

    private static final String holidayUrl = "http://tool.bitefu.net/jiari/?d=";

    private static final ConcurrentHashMap<String, Integer> holidayCache = new ConcurrentHashMap<>();

    /**
     * 获取缓存
     * @param day 日期 yyyymmdd
     */
    public static boolean getHolidayCache(String day) {

        //查询是否存在
        Integer i = holidayCache.get(day);
        if (i == null) {
            i = putHolidayCache(day);
        }
        if (Objects.equals(i, REST_DAY) || Objects.equals(i, HOLIDAY))
            return true;
        else
            return false;
    }

    /**
     * 添加到缓存
     *
     * @param day 日期 yyyymmdd
     */
    public static Integer putHolidayCache(String day) {
        String res = HttpRequest.get(holidayUrl + day)
                .timeout(2000)
                .execute().body();
        int type = Integer.parseInt(res);
        holidayCache.put(day, type);
        return type;
    }

}

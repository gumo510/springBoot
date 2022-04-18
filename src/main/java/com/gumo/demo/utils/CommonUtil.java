package com.gumo.demo.utils;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CommonUtil {
    /**
     * 反序列化操作
     *
     * @param req
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyFieldValueByJson(Object req, Class<T> target) {
        String json = JSONObject.toJSONString(req);
        return JSONObject.parseObject(json, target);
    }

    /**
     * 生成缓存有效时间 单位为s
     *
     * @return
     */
    public static int getCacheTime() {
        return getCacheTime(3600, TimeUnit.SECONDS);
    }

    public static int getCacheTime(int base, TimeUnit timeUnit) {
        //统一用s
        int baseTime;
        switch (timeUnit) {
            case HOURS:
                baseTime = base * 3600;
                break;
            case MINUTES:
                baseTime = base * 60;
                break;
            default:
                baseTime = base;
                break;
        }
        double random = Math.random();
        return (int) (Math.max(1.0 - random, random) * baseTime);
    }

    /**
     * 此函数仅用于判断早晚高峰时间数据表日期对应的类型时段
     *
     * @param queryTime
     * @return
     */
    public static Integer getPeakTimeType(String queryTime) {
        Date queryDate = DateUtil.strToDate(queryTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(queryDate);
        int month = calendar.get(Calendar.MONTH);
        //0:3月-6月,9月-12月;1:1月-2月;2:7月-8月;
        if (1 <= month && month <= 2) {
            return BigDecimal.ONE.intValue();
        } else if (7 <= month && month <= 8) {
            return 2;
        } else {
            return BigDecimal.ZERO.intValue();
        }
    }

    public static String formatRate(Double rate) {
        if (Objects.isNull(rate)) {
            rate = 0.00;
        }
        BigDecimal decimal = BigDecimal.valueOf(rate * 100);
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
    }
}

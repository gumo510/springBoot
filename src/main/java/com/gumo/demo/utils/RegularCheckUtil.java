package com.gumo.demo.utils;

import java.util.regex.Pattern;

/**
 * @author gumo
 *
 * 正则表达式工具类
 */
public class RegularCheckUtil {

    // 含有@
    private static String EMAIL_PATTERN = ".*@.*";

    // yyyy-MM-dd
    private static String DATE_PATTERN = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

    // yyyy-MM-dd HH:mm:ss
    private static String DAY_TIME_PATTERN = "^((?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)) (0[0-9]|1[0-9]|2[0-3])\\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])\\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])$";

    // HH:mm
    private static String HH_MM_PATTERN = "^(0[0-9]|1[0-9]|2[0-3])\\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])$";

    private static String TRAVEL_TIME_PATTERN = "^([0-1][0-9])|(2[0-3]):[0-5][0-9]$";

    // 手机号码校验
    private static String PHONE_PATTERN = "^[1][0-9]{10}$";

    // 中文不超过10字符, 字母不超过30字符
    private static String NAME_LENGTH = "^[\u4e00-\u9fa5]{0,10}[a-zA-Z]{0,30}$";


    public static boolean sexVerify(String sex) {
        return "男".equals(sex) || "女".equals(sex);
    }

    /**
     * 含有@的邮箱校验
     */
    public static boolean emailVerify(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    /**
     * yyyy-MM-dd
     * 日期校验
     */
    public static boolean dateVerify(String date) {
        return Pattern.matches(DATE_PATTERN, date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 日期&时间校验
     */
    public static boolean datetimeVerify(String date) {
        return Pattern.matches(DAY_TIME_PATTERN, date);
    }

    /**
     * HH:mm
     * 时间格式校验
     */
    public static boolean HHmmVerify(String date) {
        return Pattern.matches(HH_MM_PATTERN, date);
    }
    /**
     * travelTime
     * 时间格式校验
     */
    public static boolean TravelTimeVerify(String date) {
        return Pattern.matches(TRAVEL_TIME_PATTERN, date);
    }

    /**
     * 手机号码校验
     */
    public static boolean phoneVerify(String phone) {
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    /**
     * 名称长度校验
     */
    public static boolean checkName(String name) {
        return Pattern.matches(NAME_LENGTH, name);
    }

}

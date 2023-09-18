package com.gumo.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String MOBILE_REGEX = "^1[3-9]\\d{9}$";
    private static final String ID_CARD_REGEX = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
    private static final String PLATE_NUMBER_REGEX = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[-]{1}[A-Z]{1}[A-Z0-9]{5,6}$";

    public static boolean isMobile(String mobile) {
        if (mobile == null || mobile.length() == 0) {
            return false;
        }
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isIdCard(String idCard) {
        if (idCard == null || idCard.length() == 0) {
            return false;
        }
        Pattern pattern = Pattern.compile(ID_CARD_REGEX);
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches();
    }

    public static boolean isPlateNumber(String plateNumber) {
        if (plateNumber == null || plateNumber.length() == 0) {
            return false;
        }
        Pattern pattern = Pattern.compile(PLATE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(plateNumber);
        return matcher.matches();
    }


    public static void main(String[] args) {
//        String mobile = "1366742080";
//        String idCard = "4115281995051065591";
//
//        System.out.println("手机号校验结果: " + isMobile(mobile));
//        System.out.println("身份证号校验结果: " + isIdCard(idCard));

        String plateNumber1 = "粤-B88888";
        String plateNumber2 = "阿-AD1236";
        String plateNumber3 = "津-AC23456";
        String plateNumber4 = "沪-A125678";
        String plateNumber5 = "苏-EG23579";
        String plateNumber6 = "粤-Y123456";

        System.out.println("车牌校验结果1: " + isPlateNumber(plateNumber1)); // true
        System.out.println("车牌校验结果2: " + isPlateNumber(plateNumber2)); // true
        System.out.println("车牌校验结果3: " + isPlateNumber(plateNumber3)); // true
        System.out.println("车牌校验结果4: " + isPlateNumber(plateNumber4)); // true
        System.out.println("车牌校验结果5: " + isPlateNumber(plateNumber5)); // true
        System.out.println("车牌校验结果6: " + isPlateNumber(plateNumber6)); // true

    }
}
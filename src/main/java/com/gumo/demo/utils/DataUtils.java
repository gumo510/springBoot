package com.gumo.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtils {

    /**
     * 计算生日天数 days
     * @return
     */
    public static int getBirthDays(String birthday) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cToday = Calendar.getInstance(); // 存今天
        Calendar cBirth = Calendar.getInstance(); // 存生日
        int days = 0;
        try {
            cBirth.setTime(dateFormat.parse(birthday)); // 设置生日
            cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR)); // 修改为本年
            if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
                // 生日已经过了，要算明年的了
                days = (cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR)) + cBirth.get(Calendar.DAY_OF_YEAR);
            } else {
                // 生日还没过
                days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 计算恋爱天数 days
     * @return
     */
    public static int getLoveDays(String loveday){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int days = 0;
        try {
            long time = System.currentTimeMillis() - dateFormat.parse(loveday).getTime();
            days = (int) (time / (24*60*60*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

}

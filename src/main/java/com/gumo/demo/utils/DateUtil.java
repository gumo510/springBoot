package com.gumo.demo.utils;

import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Title: DataUtil.java
 * @Package com.intellif.ifaascommunitysec.util
 * @Description
 * @date 2019 12-10 10:47.
 */
public class DateUtil {

    public static final String DTF_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DTFYMDHMS = "yyyyMMddHHmmss";
    public static final String DTF_YMD = "yyyy-MM-dd";
    public static final String DTFYMD = "yyyyMMdd";
    public static final String DTFYM = "yyyyMM";
    public static final String DAY = "day";
    public static final String HOURS = "hour";
    public static final String MINUTE = "minute";
    public static final String SECOND = "second";


    public static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static DateTimeFormatter DTF_YMD_HMS_FORMAT = DateTimeFormatter.ofPattern(DTF_YMD_HMS);

    public static DateTimeFormatter DTF_YMD_FORMAT = DateTimeFormatter.ofPattern(DTF_YMD);

    public static SimpleDateFormat SDF_YMD_HMS_FORMAT = new SimpleDateFormat(DateUtil.DTF_YMD_HMS);

    public static SimpleDateFormat SDF_YMD_FORMAT = new SimpleDateFormat(DateUtil.DTF_YMD);

    public static DateTimeFormatter DTFYMD_FORMAT = DateTimeFormatter.ofPattern(DateUtil.DTFYMD);

    public static DateTimeFormatter DTFYM_FORMAT = DateTimeFormatter.ofPattern(DateUtil.DTFYM);

    public static String getYesterday() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

    }
    public static String getToday() {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

    }

    /**
     * 获取当前时间 标准格式
     * @return
     */
    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sp.format(d);

    }
    public static String getSubDate(Date date,int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sp.format(d);

    }

    public static String getSubDate2(Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

    }

    public static String getSubDate1(Date date, int days, String pattern) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat(pattern);
        return sp.format(d);

    }

    public static Date getYesterDate() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();

        return d;

    }

    public static String getTermolyday(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, +1);
        Date datec = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(datec);

    }

    public static String dateToStr(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date datec = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(datec);

    }

    public static String getTodyDateStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sp = new SimpleDateFormat(DTF_YMD);
        Date datec = calendar.getTime();
        return sp.format(datec);
    }


    public static String getYesterTwoday() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date d = cal.getTime();

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);

    }

    /**
     * 在给定的日期加上或减去指定月份后的日期
     *
     * @param sourceDate 原始时间
     * @param month      要调整的月份，向前为负数，向后为正数
     * @return
     */
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);

        return c.getTime();
    }

    /**
     * 在给定的日期加上或减去指定天数后的日期
     *
     * @param sourceDate 原始时间
     * @param date       要调整的天数，向前为负数，向后为正数
     * @return
     */
    public static Date stepDay(Date sourceDate, int date) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.DATE, date);

        return c.getTime();
    }

    /**
     * 在给定的日期加上或减去指定分钟后的日期
     *
     * @param sourceDate 原始时间
     * @param minute       要调整的分钟，向前为负数，向后为正数
     * @return
     */
    public static Date stepMinute(Date sourceDate, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MINUTE, minute);

        return c.getTime();
    }

    /**
     * 在给定的日期加上或减去指定月份后的日期
     *
     * @param sourceDate 原始时间
     * @param month      要调整的月份，向前为负数，向后为正数
     * @return
     */
    public static String stepMonth(Date sourceDate, int month, String format) {
        if (format == null) {
            format = DTF_YMD;
        }
        return date2String(stepMonth(sourceDate, month), format);
    }


    public static Date getFirstDayMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//1:本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static String date2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (Strings.isBlank(pattern)) {
            pattern = DTF_YMD_HMS;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 计算字符串时间相差的天数/小时数/分钟数
     *
     * @param startDateStr 起始时间
     * @param endDateStr   结束时间
     * @param fomatter     时间格式（eg:"yyyy-MM-dd HH:mm:ss"）
     * @param idStr        标识（day：表示天,hour:表示小时，minute：表示分钟）
     * @return
     */
    public static long subtractionDate(String startDateStr, String endDateStr, DateTimeFormatter fomatter, String idStr) {
        LocalDateTime ldt1 = LocalDateTime.parse(startDateStr, fomatter);
        LocalDateTime ldt2 = LocalDateTime.parse(endDateStr, fomatter);
        Duration duration = Duration.between(ldt1, ldt2);
        long value = 0;
        switch (idStr) {
            case DAY:
                value = duration.toDays();
                break;
            case HOURS:
                value = duration.toHours();
                break;
            case MINUTE:
                value = duration.toMinutes();
                break;
            case SECOND:
                value = duration.toMinutes();
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 比较两个时间字符大小
     *
     * @return
     */
    public static int compareDateStr(String preTime, String nextTime) {
        return LocalDateTime.parse(preTime, DateTimeFormatter.ofPattern(DTF_YMD_HMS)).
                compareTo(LocalDateTime.parse(nextTime, DateTimeFormatter.ofPattern(DTF_YMD_HMS)));

    }

    public static String transferTimeStr(String sourceTime) {
        LocalDate endLocalDate = LocalDate.parse(sourceTime, DateTimeFormatter.ofPattern(DateUtil.DTF_YMD));
        return endLocalDate.atStartOfDay().format(DateTimeFormatter.ofPattern(DateUtil.DTF_YMD_HMS));
    }


    /**
     * 获取某个时间的小时
     *
     * @param time
     * @return
     */
    public static Integer getHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getTodayTimeRange() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date endOfDate = calendar2.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DTF_YMD_HMS);
        return format.format(beginOfDate) + "~" + format.format(endOfDate);
    }

    /**
     * 判断日期是否合法
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str, String format) {
        boolean convertSuccess = true;
        if (Strings.isBlank(format)) {
            format = DTF_YMD;
        }
        if (Strings.isBlank(str) || str.length() != format.length()) {
            convertSuccess = false;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            simpleDateFormat.setLenient(false);
            try {
                simpleDateFormat.parse(str);
            } catch (ParseException e) {
                convertSuccess = false;
            }
        }

        return convertSuccess;
    }

    /**
     * 根据localDate获取几周前的星期一日期
     *
     * @param localDate
     * @param delay
     * @return
     */
    public static String getStartWeekDate(LocalDate localDate, int delay) {
        localDate = localDate.plusWeeks(delay);
        TemporalAdjuster firstOfWeek = TemporalAdjusters.ofDateAdjuster(date -> date.minusDays(date.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        LocalDate startFirstWeek = localDate.with(firstOfWeek);  //开始周开始日期
        return startFirstWeek.format(DTF_YMD_FORMAT);
    }

    /**
     * 根据localDate获取几周前的星期天日期
     *
     * @param localDate
     * @param delay
     * @return
     */
    public static String getEndWeekDate(LocalDate localDate, int delay) {
        localDate = localDate.plusWeeks(delay);
        TemporalAdjuster lastOfWeek = TemporalAdjusters.ofDateAdjuster(date -> date.plusDays(DayOfWeek.SUNDAY.getValue() - date.getDayOfWeek().getValue()));
        LocalDate endFirstWeek = localDate.with(lastOfWeek);     //开始周结束日期
        return endFirstWeek.format(DTF_YMD_FORMAT);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDate2(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     *  常用的两种时间格式字符串转换成Date
     * @param strDate 时间格式 yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd
     * @return
     */
    public static Date strToDateCheckIfContainsClockTime(String strDate) {
        String temp = strDate.trim();
        if(temp.contains(" ") && temp.contains(":")){
            return strToDate2(strDate);
        }else {
            return strToDate(strDate);
        }
    }
    /**
     * 将长时间格式字符串转换为时间 yyyyMMdd
     *
     * @param strDate
     * @return
     */
    public static String strTostr(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
        return sp.format(formatter.parse(strDate, pos));
    }

    public static String getAddMin(int min, Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MINUTE, -min);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sp.format(cal.getTime());

    }
    public static String dateToStr2(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date datec = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sp.format(datec);

    }

    /**
     * 获取当天的起始时间
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取当天的结束日期
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }

    /**
     * 获取天数
     * @param
     * @return
     */
    public static List<String> findDayStrList(Date start, Date end) {
        if (end.getTime() < start.getTime()) {
            throw new IllegalArgumentException("start time not gt end time");
        }
        SimpleDateFormat sdfYmdFormat = new SimpleDateFormat(DateUtil.DTF_YMD);
        List<String> result = new ArrayList<>();
        while (!sdfYmdFormat.format(start).equals(sdfYmdFormat.format(end))) {
            result.add(sdfYmdFormat.format(start));
            start = stepDay(start, 1);
        }
        if (result.size() == 0) {
            result.add(sdfYmdFormat.format(start));
        } else {
            result.add(sdfYmdFormat.format(end));
        }
        return result;
    }
}
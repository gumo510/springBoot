package com.gumo.demo.utils;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理多音字的扩展工具类
 *
 * @author zhangcy
 * @date 2021-12-02
 */
public class PinYinMultiCharactersUtils {

    private static final Logger logger = LoggerFactory.getLogger(PinYinMultiCharactersUtils.class);

    private static Map<String, List<String>> pinyinMap = new HashMap<>();

    private static Map<String, List<String>> otherSpecialWord = new HashMap<>();

    static {
        //这里如果apollo上面没有配置任何的值，默认初始化一些常用的
        otherSpecialWord.put("解", Arrays.asList("xie"));
        otherSpecialWord.put("查", Arrays.asList("zha"));
        otherSpecialWord.put("单", Arrays.asList("shan"));
        otherSpecialWord.put("朴", Arrays.asList("piao"));
        otherSpecialWord.put("区", Arrays.asList("ou"));
        otherSpecialWord.put("仇", Arrays.asList("qiu"));
        otherSpecialWord.put("阚", Arrays.asList("kan"));
        otherSpecialWord.put("种", Arrays.asList("chong"));
        otherSpecialWord.put("盖", Arrays.asList("ge"));
        otherSpecialWord.put("繁", Arrays.asList("po"));

    }

    public static String toPinyin(String str) {
        try {
            initPinyin("/duoyinzi.dic.txt");
            String py = convertChineseToPinyin(str);
            System.out.println(str + " = " + py);
            return py;
        } catch (Exception e) {
            logger.error("convert pinyin error,e : {}", e);
            return null;
        }
    }

    /**
     * 通过拆分名字的方式 获取多音字的名字的完整拼音
     *
     * @param chinese
     * @return
     */
    public static String getMultiCharactersPinYin(String chinese) {
        if (StringUtils.isEmpty(chinese)) {
            return null;
        }
        String result = null;
        if (chinese.length() >= 2) {
            String[] nameElements = chinese.split("");
            String firstName = nameElements[0];
            if (!isMultiChineseWord(firstName)) {
                return null;
            }
            String secondName = null;
            StringBuilder sb = new StringBuilder();
            for (String str : nameElements) {
                if (!str.equals(firstName)) {
                    sb.append(str);
                }
            }
            secondName = sb.toString();
            //获取多音字的拼音
            String partOne = PinYinMultiCharactersUtils.toPinyin(firstName);
            String partTwo = PinYinMultiCharactersUtils.toPinyin(secondName);
            result = partOne.concat(partTwo).toLowerCase();
        } else {
            result = PinYinMultiCharactersUtils.toPinyin(chinese);
        }
        return result;
    }

    /**
     * 将某个字符串的首字母大写
     *
     * @param str
     * @return
     */
    public static String convertInitialToUpperCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (i == 0) {
                sb.append(String.valueOf(ch).toUpperCase());
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    /**
     * 判断当前中文字是否多音字
     *
     * @param chinese
     * @return
     */
    public static boolean isMultiChineseWord(String chinese) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (ch > 128) {
                // 非ASCII码,取得当前汉字的所有全拼
                try {
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
                    if (results == null) {
                        //非中文
                        return false;
                    } else {
                        int len = results.length;
                        if (len == 1) {
                            // 不是多音字
                            return false;
                        } else if (results[0].equals(results[1])) {
                            //非多音字 有多个音，默认取第一个
                            if (otherSpecialWord.containsKey(chinese)) {
                                return true;
                            }
                            return false;
                        } else {
                            // 多音字
                            return true;
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error("BadHanyuPinyinOutputFormatCombination ,e :{}", e);
                }
            }
        }
        return false;
    }

    /**
     * 汉字转拼音 最大匹配优先
     *
     * @param chinese
     * @return
     */
    private static String convertChineseToPinyin(String chinese) {
        StringBuffer pinyin = new StringBuffer();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (ch > 128) {
                // 非ASCII码 取得当前汉字的所有全拼
                try {
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
                    if (results == null) {  //非中文
                        return "";
                    } else {
                        int len = results.length;
                        if (len == 1) {
                            // 不是多音字
                            String py = results[0];
                            if (py.contains("u:")) {  //过滤 u:
                                py = py.replace("u:", "v");
                                logger.info("filter u: {}", py);
                            }
                            pinyin.append(convertInitialToUpperCase(py));
                        } else if (results[0].equals(results[1])) {
                            //非多音字 有多个音，取第一个
                            if (otherSpecialWord.containsKey(chinese)) {
                                return otherSpecialWord.get(chinese).get(0);
                            }
                            pinyin.append(convertInitialToUpperCase(results[0]));
                        } else {
//                            logger.info("多音字：{}", ch);
                            if (otherSpecialWord.containsKey(chinese)) {
                                pinyin.append(otherSpecialWord.get(chinese).get(0));
                                continue;
                            }
                            int length = chinese.length();
                            boolean flag = false;
                            String s = null;
                            List<String> keyList = null;
                            for (int x = 0; x < len; x++) {
                                String py = results[x];
                                if (py.contains("u:")) {
                                    py = py.replace("u:", "v");
                                    logger.info("filter u ：{}", py);
                                }
                                keyList = pinyinMap.get(py);
                                if (i + 3 <= length) {
                                    //后向匹配2个汉字  大西洋
                                    s = chinese.substring(i, i + 3);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if (i + 2 <= length) {
                                    //后向匹配 1个汉字  大西
                                    s = chinese.substring(i, i + 2);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 2 >= 0) && (i + 1 <= length)) {
                                    // 前向匹配2个汉字 龙固大
                                    s = chinese.substring(i - 2, i + 1);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 1 >= 0) && (i + 1 <= length)) {
                                    // 前向匹配1个汉字   固大
                                    s = chinese.substring(i - 1, i + 1);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 1 >= 0) && (i + 2 <= length)) {
                                    //前向1个，后向1个  固大西
                                    s = chinese.substring(i - 1, i + 2);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (!flag) {
                                //都没有找到，匹配默认的 读音  大
                                s = String.valueOf(ch);
                                for (int x = 0; x < len; x++) {
                                    String py = results[x];
                                    if (py.contains("u:")) {  //过滤 u:
                                        py = py.replace("u:", "v");
                                    }
                                    keyList = pinyinMap.get(py);
                                    if (keyList != null && (keyList.contains(s))) {
                                        pinyin.append(convertInitialToUpperCase(py));//拼音首字母 大写
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error("BadHanyuPinyinOutputFormatCombination :{}", e);
                }
            } else {
                pinyin.append(arr[i]);
            }
        }
        return pinyin.toString();
    }

    /**
     * 初始化 所有的多音字词组
     *
     * @param fileName
     */
    public static void initPinyin(String fileName) {
        if (pinyinMap != null && !pinyinMap.isEmpty()) {
            return;
        }

//        // 使用Apollo获取配置，读取多音字的全部拼音表;
//        Config config = ConfigService.getAppConfig();
//        String fileContent = config.getProperty(fileName, null);
//
//        if (fileContent == null) {
//            logger.error("File content is not found in Apollo configuration");
//            return;
//        }
//
//        // 将文件内容转换为InputStream
//        InputStream file = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
//        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        // 读取多音字的全部拼音表;
        InputStream file = PinyinHelper.class.getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));

        String s = null;
        try {
            while ((s = br.readLine()) != null) {
                if (s != null) {
                    String[] arr = s.split("#");
                    String pinyin = arr[0];
                    String chinese = arr[1];
                    if (chinese != null) {
                        String[] strs = chinese.split("/");
                        List<String> list = Arrays.asList(strs);
                        pinyinMap.put(pinyin, list);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("IOException,{}", e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                logger.error("IOException,{}", e);
            }
        }
    }

}

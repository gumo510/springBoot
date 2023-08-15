package com.gumo.demo.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文名字转拼音工具类
 *
 * @author zhangcy
 * @date 2021-11-04
 */
@Slf4j
public class PinYinUtils {

    /**
     * 提取每个汉字的首字母(大写)
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        if (isNull(str)) {
            return "";
        }
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            }
            else {
                convert += word;
            }
        }
        if (isNull(convert)) {
            return "";
        }
        convert = convert.trim().replace(" ", "");
        return convert.toLowerCase();
    }

    /**
     * 首字母大写
     *
     * @param name 参数中文字符串
     * @return result
     * @throws {@link BadHanyuPinyinOutputFormatCombination}
     */
    public static String getChinesePinyinFromName(String name) {
        String result = null;
        try {
            HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
            pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            result = PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isChineseName(String name) {
        boolean result = true;
        if (StringUtils.isNotEmpty(name)) {
            String[] strChars = name.split("");
            for (String singleStr : strChars) {
                if (!isContainChinese(singleStr)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String getChineseFirstPingYingName(String str) {
        String[] split = str.split("");
        return PinYinUtils.getChinesePinyinFromName(split[0]);
    }

    public static String convert(String chineseName) {
        String nameVar1 = getChinesePinyinFromName(chineseName);
        String nameVar2 = getPinYinHeadChar(chineseName);
        String nameVar3 = getChineseFirstPingYingName(chineseName);
        String result = nameVar1 + "," + nameVar2 + "," + nameVar3;
        return result;
    }

    public static boolean isMixedStr(String realname) {
        String[] splitStr = realname.split("");
        List<String> allStrs = Arrays.asList(splitStr);
        List<String> allLastStr = new ArrayList<>();
        boolean hasChinese = false;
        for (String single : splitStr) {
            if (isChineseName(single)) {
                hasChinese = true;
            } else {
                allLastStr.add(single);
            }
        }
        if (hasChinese) {
            if (CollectionUtils.isNotEmpty(allStrs) && CollectionUtils.isNotEmpty(allLastStr) && allLastStr.size() < allStrs.size()) {
                hasChinese = true;
            }
        }
        return hasChinese;
    }

    /*
     * 判断字符串是否为空
     */

    public static boolean isNull(Object strData) {
        if (strData == null || String.valueOf(strData).trim().equals("")) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        String chineseName = "沐栋";
        String nameVar1 = PinYinUtils.getChinesePinyinFromName(chineseName);
        String nameVar2 = PinYinUtils.getPinYinHeadChar(chineseName);
        String nameVar3 = PinYinUtils.getChineseFirstPingYingName(chineseName);
        System.out.println(nameVar1);
        System.out.println(nameVar2);
        System.out.println(nameVar3);
    }

    /**
     * 如果是中文何字符串等混合过来的，只需原样解析，比如：111董aaa飞飞333 ，解析为：111dongaaafeifei333
     *
     * @param realname
     * @return
     */
    public static String getMixPinyinStr(String realname) {
        if (StringUtils.isEmpty(realname)) {
            return null;
        }
        String[] splitStr = realname.split("");
        StringBuilder stringBuilder = new StringBuilder();
        int firstIndex = 0;
        for (String single : splitStr) {
            if (isChineseName(single)) {
                //只有第一个中文多音字做解析
                if (firstIndex == 0 && PinYinMultiCharactersUtils.isMultiChineseWord(single)) {
                    String chinesePinyinFromName = PinYinMultiCharactersUtils.getMultiCharactersPinYin(single);
                    stringBuilder.append(chinesePinyinFromName);
                    continue;
                }
                String chinesePinyinFromName = getChinesePinyinFromName(single);
                stringBuilder.append(chinesePinyinFromName);
                firstIndex++;
            } else {
                stringBuilder.append(single);
            }
        }
        return stringBuilder.toString();
    }
}



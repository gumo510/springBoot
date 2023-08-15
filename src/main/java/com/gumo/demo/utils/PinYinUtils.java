package com.gumo.demo.utils;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;

/**
 * 中文名字转拼音工具类
 *
 * @author gumo
 * @date 2023-08-15
 */
@Slf4j
public class PinYinUtils {

    public static String convert(String chineseName) {
        Set<String> nameVar1 = PinYinUtils.getPinyinOfName(chineseName);
        Set<String> nameVar2 = PinYinUtils.getPinYinHeadChar(chineseName);
        String result = Joiner.on(",").join(nameVar1) + "," + Joiner.on(",").join(nameVar2);
        return result;
    }

    /**
     * 提取每个汉字的首字母(支持多音字)
     *
     * @param str
     * @return
     */
    public static Set<String> getPinYinHeadChar(String str) {
        if (isNull(str)) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        getPinYinHeadCharHelper(str, 0, "", result);
        return result;
    }

    private static void getPinYinHeadCharHelper(String str, int index, String currentAbbreviation, Set<String> result) {
        if (index >= str.length()) {
            result.add(currentAbbreviation.toLowerCase());
            return;
        }

        char word = str.charAt(index);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
        if (pinyinArray != null) {
            for (String pinyin : pinyinArray) {
                getPinYinHeadCharHelper(str, index + 1, currentAbbreviation + pinyin.charAt(0), result);
            }
        }
        else {
            getPinYinHeadCharHelper(str, index + 1, currentAbbreviation + word, result);
        }
    }

    private static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 提取每个汉字的拼音字母(支持多音字)
     *
     * @param name 参数中文字符串
     * @return result
     * @throws {@link BadHanyuPinyinOutputFormatCombination}
     */
    public static Set<String> getPinyinOfName(String name) {
        if (isNull(name)) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        getPinyinOfNameHelper(name, 0, "", result);
        return result;
    }

    private static void getPinyinOfNameHelper(String name, int index, String currentPinyin, Set<String> result) {
        if (index >= name.length()) {
            result.add(currentPinyin);
            return;
        }

        char ch = name.charAt(index);
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        try {
            String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat);
            if (pinyins != null && pinyins.length > 0) {
                for (String pinyin : pinyins) {
                    getPinyinOfNameHelper(name, index + 1, currentPinyin + pinyin, result);
                }
            } else {
                getPinyinOfNameHelper(name, index + 1, currentPinyin + ch, result);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
    }

    public static boolean containsChinese(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (isChinese(ch)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char ch) {
        //中文字符Unicode范围：\u4e00-\u9fa5
        return ch >= 0x4E00 && ch <= 0x9FA5;
    }

    /**
     * 1、pinYinUtils.containsChinese 判断传入过来的名称是否是中文呢？如果全部是中文的话做基础的解析
     * 2、pinYinUtils.convert 做拼音转换
     * 3、如果用户名中不全是中文，比如： 周小斌_bank_1 ,类似这样的，或者 : bank_1_周小斌 ，只转换其中的中文，不改变整个字符串的顺序
     */
    public static void main(String[] args) {
        String chineseName = "曾脉_001";
        boolean flag = containsChinese(chineseName);
        Set<String> nameVar1 = PinYinUtils.getPinyinOfName(chineseName);
        Set<String> nameVar2 = PinYinUtils.getPinYinHeadChar(chineseName);
        System.out.println(flag);
        System.out.println(nameVar1);
        System.out.println(nameVar2);
        System.out.println(Joiner.on(",").join(nameVar1) + "," + Joiner.on(",").join(nameVar2));
    }
}

package com.gumo.demo.utils;

/**
 * 文件工具类
 *
 * @author gumo
 * @date 2021/09/03
 */
public class FileUtil {

    /**
     * 检查文件大小
     *
     * @param len  len
     * @param size 大小
     * @param unit 单位
     * @return boolean
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }
}

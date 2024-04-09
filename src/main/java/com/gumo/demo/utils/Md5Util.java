package com.gumo.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    /**
     * 字符串MD5编码
     *
     * @param input 输入字符串
     * @return String 编码后的字符串
     * @see Md5Util
     */
    public static String encodeMD5(String input) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            md5.update(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : md5.digest()) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * 文件MD5编码
     *
     * @param file
     * @return
     */
    public static String encodeMD5File(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] dataBytes = new byte[1024];

            int bytesRead;
            while ((bytesRead = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, bytesRead);
            }

            byte[] mdBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte mdByte : mdBytes) {
                sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
            }

            fis.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void main(String[] args) {
        File file = new File("D:\\文档\\相关项目\\通行产品\\设备升级文件\\测试\\ai-mbj-v2.4.7-rk-m510-150-lite-20240128\\ai-mbj-v2.4.7-rk-m510-150-lite-20240128.bin");
        String md5String = "ad8daf10b64d3076e60d2543a7468996";
        String md5Str = encodeMD5File(file);
        System.out.println("MD5 Match: " + md5String.equals(md5Str));
    }

}

package com.gumo.demo.utils;

import org.apache.http.entity.ContentType;
//import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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


    public static MultipartFile base64TOFile(String base64Str) {
        try {
            byte[] faceBytes = Base64Utils.decodeFromString(base64Str);
            InputStream inputStream = new ByteArrayInputStream(faceBytes);
//            MultipartFile multipartFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String FileTOBase64(MultipartFile file) throws Exception{
        String imageStr = Base64.getEncoder().encodeToString(file.getBytes());
        return imageStr;
    }

}

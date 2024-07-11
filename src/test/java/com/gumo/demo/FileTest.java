package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gumo.demo.model.dto.AreaExportMsgParam;
import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.utils.ExcelUtil;
import com.gumo.demo.utils.SslUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileTest {

    /**
     * 解压zip 获取config 与bin 文件
     * @param zipFile
     * @return
     * @throws IOException
     */
    private Object extractUpgradeZip(File zipFile) throws IOException {
        File tempFile = null;

        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis, Charset.forName("gbk"))) {

            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    byte[] data = new byte[(int) ze.getSize()];
                    zis.read(data, 0, (int) ze.getSize());

                    String[] arrNames = ze.getName().split("/");
                    String name = arrNames[arrNames.length - 1];
                    if (name.equals("config.json")) {
                        String jsonStr = new String(data);
//                     UpgradeZipConfig upgradeZipConfig = JSONObject.parseObject(jsonStr, UpgradeZipConfig.class);
                    } else {
                        tempFile = File.createTempFile("upload", ".bin");
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(data);
                        }
                    }
                }
            }
        }

        if (tempFile != null) {
            tempFile.deleteOnExit();
        }

        return null;
    }


    /**
     * 解压获取bin 文件
     * @param zipFile
     * @return
     * @throws Exception
     */
    private File extractBinFile(File zipFile) throws Exception {
        // ZIP文件中提取".bin"文件
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                String zipEntryName = zipEntry.getName();
                if (zipEntryName.endsWith(".bin")) {
                    File tempFile = File.createTempFile("upload", ".bin");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    return tempFile;
                }
            }
        }

        return null; // 未找到.bin文件
    }

    private CommonResult excelErrorMsgExport(List<AreaExportMsgParam> msgList) {
        try {
            List<String> headers = new ArrayList<>();
            List<String> ds_titles = new ArrayList<>();
            List<Map<String, Object>> data = new ArrayList<>();

            headers.add("设备名字");
            headers.add("异常信息");

            ds_titles.add("name");
            ds_titles.add("msgException");

            for (AreaExportMsgParam exportMsgParam : msgList) {
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("name", exportMsgParam.getName());
                dataMap.put("msgException", exportMsgParam.getMsgException());
                data.add(dataMap);
            }

            byte[] fileData = ExcelUtil.export(null, null, headers, ds_titles, null, null, data);
//            String path = fileStoreFactory.getFileStoreStrategyService().upload(fileData, "xlsx", null);

            return CommonResult.failed("导出异常文件失败");
        } catch (IOException e) {
            return CommonResult.failed("导出异常文件失败");
        }
    }


    /**
     * https 跳过验证
     * @throws Exception
     */
    @Test
    public void testCommandToJson() throws Exception {
        readImageBytes("https://192.168.12.56:38089/fusionfsintellif202/fs01/20240703/023/20240703T105501_50899_61b0d9e65da40-f0.jpg");
        System.out.println("******");
    }

    public static byte[] readImageBytes(String url) throws Exception {
//        // 创建忽略SSL验证的RestTemplate实例
//        RestTemplate sslRestTemplate = SslUtils.getIgnoreSslRestTemplate();
//        // 使用RestTemplate下载图片
//        byte[] bytes;
//        try (InputStream inputStream = sslRestTemplate.getForObject(url, InputStream.class);
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, length);
//            }
//            bytes = outputStream.toByteArray();
//        }
//
//        return bytes;

//        disableSslVerification();
        URL urlObj = new URL(url);
        byte[] bytes = new byte[1024];
        URLConnection urlConnection = urlObj.openConnection();
        urlConnection.setConnectTimeout(30000);
        urlConnection.setReadTimeout(60000);
        // 添加请求头 反盗链
        urlConnection.setRequestProperty("Referer", "https://swd.vesionbook.com:38065/");
        try (InputStream inputStream = urlConnection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            return outputStream.toByteArray();
        }
    }

    static {
        // 创建忽略SSL验证代码只会被执行一次
        disableSslVerification();
    }
    public static void disableSslVerification() {
        // 创建一个信任所有证书的TrustManager
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };


        try {
            // 初始化SSLContext
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            // 设置SSLContext到HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // 创建一个HostnameVerifier，它不做任何检查
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true; // 不检查主机名
                }
            };

            // 安装所有主机名验证器
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.gumo.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author gumo
 */
@Slf4j
public class HttpUtil {
    /**
     * get请求，参数拼接在地址上
     *
     * @param url 请求地址加参数
     * @return 响应
     */
    public static String get(String url, Map<String, String> headerMap) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        // 设置超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(10000).build();
        get.setConfig(requestConfig);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (String key : headerMap.keySet())
                get.setHeader(key, headerMap.get(key));
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response != null)
                System.out.println(".....Get return code:" + response.getStatusLine().getStatusCode());
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String url, Map<String, String> headerMap, String parmValue) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        // 设置超时
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(10000).build();
        post.setConfig(requestConfig);

        // 构造消息头
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Connection", "Close");

        if (headerMap != null && !headerMap.isEmpty()) {
            for (String key : headerMap.keySet())
                post.setHeader(key, headerMap.get(key));
        }

        StringEntity inEntity = new StringEntity(parmValue, Charset.forName("UTF-8"));
        inEntity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        inEntity.setContentType("application/json");
        post.setEntity(inEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            if (response != null)
                System.out.println(".....Get return code:" + response.getStatusLine().getStatusCode());
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * post请求接口
     * @param url
     * @param file
     * @param headerParams
     * @param otherParams
     * @return
     */
//    public static String postResultMultipartFile(String url, MultipartFile file, Map<String,String> headerParams, Map<String,String> otherParams) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String result = "";
//        HttpEntity httpEntity = null;
//        HttpEntity responseEntity = null;
//        try {
//            String fileName = file.getOriginalFilename();
//            HttpPost httpPost = new HttpPost(url);
//            //添加header
//            for (Map.Entry<String, String> e : headerParams.entrySet()) {
//                httpPost.addHeader(e.getKey(), e.getValue());
//            }
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setCharset(Charset.forName("utf-8"));
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
//            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//            for (Map.Entry<String, String> e : otherParams.entrySet()) {
//                builder.addTextBody(e.getKey(), e.getValue());// 类似浏览器表单提交，对应input的name和value
//            }
//            httpEntity = builder.build();
//            httpPost.setEntity(httpEntity);
//            HttpResponse response = httpClient.execute(httpPost);// 执行提交
//            responseEntity = response.getEntity();
//            if (responseEntity != null) {
//                // 将响应内容转换为字符串
//                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
//            }
//        } catch (IOException e) {
//            log.info("------------------{}",e.getMessage());
//            e.printStackTrace();
//        }catch (Exception e) {
//            log.info("------------------{}",e.getMessage());
//            e.printStackTrace();
//        }
//        return result;
//    }
}

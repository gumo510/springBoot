package com.gumo.demo.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import okhttp3.OkHttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.TextUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"Convert2Lambda"})
public class SslUtils {

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    @SuppressWarnings({"unused", "RedundantThrows"})
    static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }


        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }


        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }
    }


    /**
     * 忽略SSL证书校验
     * @throws Exception e
     */
    public static void ignoreSsl() throws Exception {
        javax.net.ssl.HostnameVerifier hv = new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String urlHostName, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    /**
     * Created with IDEA
     * Author: www.itze.cn
     * Date: 2021-02-24
     * Email：gitlab@111.com
     * okhttp忽略所有SSL证书认证
     *
     * @return
     */
    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) (trustAllCerts[0]));
            builder.hostnameVerifier(new HostnameVerifier() {
                //这里存放不需要忽略SSL证书的域名，为空即忽略所有证书
                String[] ssls = {};

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (TextUtils.isEmpty(hostname)) {
                        return false;
                    }
                    return !Arrays.asList(ssls).contains(hostname);
                }
            });

            OkHttpClient okHttpClient = builder.connectTimeout(10, TimeUnit.MINUTES).
                    writeTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).retryOnConnectionFailure(true).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 跳过证书效验的sslcontext
     *
     * @return
     * @throws Exception
     */
    private static SSLContext createIgnoreVerifySSL() throws Exception {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    /**
     * 构造RestTemplate
     *
     * @return
     * @throws Exception
     */
    public static RestTemplate getIgnoreSslRestTemplate() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 超时
        factory.setConnectionRequestTimeout(5000);
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(),
                // 指定TLS版本
                null,
                // 指定算法
                null,
                // 取消域名验证
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String string, SSLSession ssls) {
                        return true;
                    }
                });
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        // 解决中文乱码问题
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 构造HttpClientBuilder
     *
     * @return
     * @throws Exception
     */
    public static HttpClientBuilder getIgnoreSslHttpClientBuilder() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 超时
        factory.setConnectionRequestTimeout(5000);
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(),
                // 指定TLS版本
                null,
                // 指定算法
                null,
                // 取消域名验证
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String string, SSLSession ssls) {
                        return true;
                    }
                });
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf);
        return httpClientBuilder;
    }

    /**
     * 构造HttpRequest hu
     *
     * @return
     * @throws Exception
     */
    public static HttpRequest getIgnoreSslHttpRequest(String url,Method cMethod) throws Exception {
        HttpRequest tHttpRequest = new HttpRequest(url);
        tHttpRequest.setMethod(cMethod);
        return tHttpRequest.setSSLSocketFactory(createIgnoreVerifySSL().getSocketFactory());
    }

}

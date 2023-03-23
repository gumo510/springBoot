package com.gumo.demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

/**
 * @author gumo
 * @version V1.0
 * @Description
 * @date 2022 04-26 12:03.
 */
@Data
@Configuration
@Slf4j
public class ElasticSearchConfig {

    @Value("${elasticsearch.scheme:https}")       //http
    private String scheme;
    @Value("${elasticsearch.host:192.168.2.11}")  //127.0.0.1
    private String host;
    @Value("${elasticsearch.port:30002}")         //9200
    private Integer port;
    @Value("${elasticsearch.username:intellif}")  //elastic
    private String username;
    @Value("${elasticsearch.password:Intellifusion@01}") //introcks1234
    private String password;

    @Value("${elasticsearch.maxRetryTimeoutMillis:60000}")
    private Integer maxRetryTimeoutMillis;
    @Value("${elasticsearch.connectTimeout:5000}")
    private Integer connectTimeout;
    @Value("${elasticsearch.socketTimeout:60000}")
    private Integer socketTimeout;

    @Value("${elasticsearch.maxConnectNum:200}")
    private Integer maxConnectNum; // 最大连接数
    @Value("${elasticsearch.maxConnectPerRoute:200}")
    private Integer maxConnectPerRoute; // 最大路由连接数

    @Bean(name = "elasticSearchClient")
    public RestHighLevelClient highLevelClient() {
        RestClientBuilder clientBuilder = null;
        try {
            // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是IP，参数2是端口，参数3是通信协议
            clientBuilder = RestClient.builder(new HttpHost(host, port, scheme));
            // 设置Header编码
            Header[] defaultHeaders = {new BasicHeader("content-type", "application/json")};
            clientBuilder.setDefaultHeaders(defaultHeaders);

            // 设置超时时间，多次尝试同一请求时应该遵守的超时。默认值为30秒，与默认套接字超时相同。若自定义套接字超时，则应相应地调整最大重试超时
//            clientBuilder.setMaxRetryTimeoutMillis(maxRetryTimeoutMillis);
            // 配置请求超时，将连接超时（默认为1秒）和套接字超时（默认为30秒）增加，
            // 这里配置完应该相应地调整最大重试超时（默认为30秒），即上面的setMaxRetryTimeoutMillis，一般于最大的那个值一致即60000
            clientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
                // 连接5秒超时，套接字连接60s超时
                return requestConfigBuilder.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout);
            });

            // HTTPS适配
            if ("https".equals(scheme)) {
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                SSLContext sslContext =
                        new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
                SSLIOSessionStrategy sessionStrategy =
                        new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);
                clientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.disableAuthCaching()
                        .setSSLStrategy(sessionStrategy).setDefaultCredentialsProvider(credentialsProvider)
                        .setMaxConnTotal(maxConnectNum).setMaxConnPerRoute(maxConnectPerRoute)
                        .setKeepAliveStrategy((response, context) -> 1000 * 60));
            } else {
                clientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setMaxConnTotal(maxConnectNum).setMaxConnPerRoute(maxConnectPerRoute)
                        .setKeepAliveStrategy((httpResponse, httpContext) -> 1000 * 60));
            }
        } catch (Exception e) {
            log.error("SkyGraphESFactory TransportClient create error!!", e);
        }

        return new RestHighLevelClient(clientBuilder);
    }

    /*private String host;
    private Integer port;
    
    public RestHighLevelClient newRestHighLevelClient() {
        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是IP，参数2是端口，参数3是通信协议
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port, "http"));
        // 设置Header编码
        Header[] defaultHeaders = {new BasicHeader("content-type", "application/json")};
        clientBuilder.setDefaultHeaders(defaultHeaders);
        // 设置超时时间，多次尝试同一请求时应该遵守的超时。默认值为30秒，与默认套接字超时相同。若自定义套接字超时，则应相应地调整最大重试超时
        clientBuilder.setMaxRetryTimeoutMillis(30000);
        // 配置请求超时，将连接超时（默认为1秒）和套接字超时（默认为30秒）增加，
        // 这里配置完应该相应地调整最大重试超时（默认为30秒），即上面的setMaxRetryTimeoutMillis，一般于最大的那个值一致即60000
        clientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            // 连接5秒超时，套接字连接60s超时
            return requestConfigBuilder.setConnectTimeout(15000).setSocketTimeout(30000);
        });
        return new RestHighLevelClient(clientBuilder);
    }*/

}

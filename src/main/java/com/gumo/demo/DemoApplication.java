package com.gumo.demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;


@Import(FdfsClientConfig.class)     // Fdfs注册
//@EnableApolloConfig({"application","vesionbook.common", "vesionbook.job"}) // Apollo
//@EnableConfigurationProperties()    // 开启 配置 @ConfigurationProperties
//@EnableHystrix                      // 开启 Hystrix
//@EnableDiscoveryClient              // 让注册中心能够发现，扫描到改服务
@EnableFeignClients                 // 开启 feign @FeignClient
@EnableScheduling                   // 开启 定时任务 @Scheduled
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@MapperScan("com.gumo.demo.mapper") //扫描的mapper
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)    //添加排除原生Druid的配置类。
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

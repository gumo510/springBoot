package com.gumo.demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;


@Import(FdfsClientConfig.class)     // Fdfs注册
//@EnableApolloConfig({"application","vesionbook.common", "vesionbook.job"}) // Apollo
@EnableFeignClients                 // 开启 feign
@EnableScheduling                   // 开启 定时任务
@MapperScan("com.gumo.demo.mapper") //扫描的mapper
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)    //添加排除原生Druid的配置类。
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@EnableConfigurationProperties()
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

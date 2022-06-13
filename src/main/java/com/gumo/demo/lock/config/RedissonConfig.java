package com.gumo.demo.lock.config;


import com.gumo.demo.lock.DistributedLockTemplate;
import com.gumo.demo.lock.SingleDistributedLockTemplate;
import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redisson() {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port);
        if (!StringUtil.isEmpty(password)) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    public DistributedLockTemplate distributedLockTemplate(RedissonClient redissonClient) {
        return new SingleDistributedLockTemplate(redissonClient);
    }
}

package com.gumo.demo.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.annotations.Document;


import java.util.Set;

/**
 * 在springboot整合spring data elasticsearch项目中，当索引数量较多，mapping结构较为复杂时，
 * 我们常常希望启动项目时能够自动创建索引及mapping，这样就不用再到各个环境中创建索引了
 *
 * 我们已经在实体类中声明了索引数据结构了，只需要识别有@Document注解的实体类，
 * 然后调用ElasticsearchRestTemplate的createIndex和putMapping方法即可创建索引及mapping
 */
@Configuration
@Slf4j
@AllArgsConstructor
@ConditionalOnBean(ElasticsearchRestTemplate.class)
public class ElasticCreateIndexStartUp implements ApplicationListener<ContextRefreshedEvent> {

    private final ElasticsearchRestTemplate restTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        log.info("[elastic]索引初始化...");
        Reflections f = new Reflections("com.gumo.demo.elasticsearch.pojo");
        Set<Class<?>> classSet = f.getTypesAnnotatedWith(Document.class);
        for (Class<?> clazz : classSet) {
            IndexOperations indexOperations = restTemplate.indexOps(clazz);
            if(!indexOperations.exists()){
                indexOperations.create();
                indexOperations.putMapping();
                log.info(String.format("[elastic]索引%s数据结构创建成功",clazz.getSimpleName()));
            }
        }
        log.info("[elastic]索引初始化完毕");
    }

}



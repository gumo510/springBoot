package com.gumo.demo.cache;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.mapper.BaseTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class InitCache {

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    @PostConstruct
    public void init() {
        log.info("init cache start...");
        CarTypeCache.setCarTypeMap(getCarTypeMap());
        log.info("init cache end...");
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void updateCache() {
        log.info("[Scheduled] update Cache start...");
        CarTypeCache.clear();
        CarTypeCache.setCarTypeMap(getCarTypeMap());
        log.info("[Scheduled] update Cache end...");
    }


    private Map<String, String> getCarTypeMap() {
        return baseTypeMapper.selectBaseBusTypes().stream().filter(carType -> Objects.nonNull(carType.getTypeName())).collect(Collectors.toMap(BaseType::getTypeName, JSON::toJSONString));
    }
}

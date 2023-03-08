package com.gumo.demo.cache;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gumo.demo.constants.RedisConstants;
import com.gumo.demo.model.vo.BusTypeCrowedVO;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.mapper.BaseTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *   车型缓存拥挤度
 * </p>
 *
 * @author hy
 * @since 2022-02-21 10:56
 */
@Component
@Slf4j
public class BusTypeCache {

    private final BaseTypeMapper baseTypeMapper;
    private final RedisTemplate redisTemplate;

    @Value("${redis.bus.type.key.timeout:12}")
    private Integer keyTimeOut;


    @Autowired
    public BusTypeCache(BaseTypeMapper baseTypeMapper, RedisTemplate<String, String> redisTemplate) {
        this.baseTypeMapper = baseTypeMapper;
        this.redisTemplate = redisTemplate;
    }

//    @PostConstruct
    public void init() {
        log.info("init postConstruct start...");
        Map<String, String> carTypeMap = baseTypeMapper.selectBaseBusTypes().stream().filter(carType -> Objects.nonNull(carType.getTypeName())).collect(Collectors.toMap(BaseType::getTypeName, JSON::toJSONString));
        CarTypeCache.setCarTypeMap(carTypeMap);
    }

    public BusTypeCrowedVO getBusTypeCacheByTypeName(String typeName) {
        BusTypeCrowedVO result = null;
        String redisKey = RedisConstants.BUS_TYPE_CACHE;
        if (redisTemplate.hasKey(redisKey)) {
            String value = (String) redisTemplate.opsForHash().get(redisKey, typeName);
            if (Objects.nonNull(value)) {
                result = JSON.parseObject(value, BusTypeCrowedVO.class);
            }
            return result;
        }
        return init(typeName);
    }

    private BusTypeCrowedVO init(String typeName) {
        BusTypeCrowedVO result = null;
        List<BaseType> baseBusTypes = baseTypeMapper.selectBaseBusTypes();
        //将车型拥挤度存入缓存，key 为 车型type_name，value为拥挤度集合
        String redisKey = RedisConstants.BUS_TYPE_CACHE;
        if (CollectionUtils.isEmpty(baseBusTypes)) {
            redisTemplate.opsForHash().put(redisKey, "-1", "[]");
        } else {
            Map<String, BaseType> collect = baseBusTypes.stream().collect(Collectors.toMap(bi -> bi.getTypeName(), bi -> bi));
            Map<String, String> busTypeCrowed = new HashMap<>(collect.size());
            collect.forEach((k, v) -> {
                busTypeCrowed.put(k, JSON.toJSONString(buildBusTypeCrowed(v)));
            });
            redisTemplate.opsForHash().putAll(redisKey, busTypeCrowed);
            String value = busTypeCrowed.get(typeName);
            if (Objects.nonNull(value)) {
                result = JSON.parseObject(value, BusTypeCrowedVO.class);
            }
        }
        redisTemplate.expire(redisKey, keyTimeOut, TimeUnit.HOURS);
        return result;
    }

    private BusTypeCrowedVO buildBusTypeCrowed(BaseType baseBusType) {
        BusTypeCrowedVO busTypeCrowedVO = new BusTypeCrowedVO();
        busTypeCrowedVO.setTypeName(baseBusType.getTypeName());
        try {
            if(StrUtil.isNotBlank(baseBusType.getLooseLimit())){
                String[] loose = baseBusType.getLooseLimit().split("<");
                busTypeCrowedVO.setMaxLooseLimit(Integer.valueOf(loose[1].trim()));
            }

            if(StrUtil.isNotBlank(baseBusType.getNormalCrowedLimit())){
                String[] normalCrowed = baseBusType.getNormalCrowedLimit().split("-");
                busTypeCrowedVO.setMaxNormalCrowedLimit(Integer.valueOf(normalCrowed[1].trim()));
            }

            if(StrUtil.isNotBlank(baseBusType.getMidCrowedLimit())){
                String[] midCrowed = baseBusType.getMidCrowedLimit().split("-");
                busTypeCrowedVO.setMaxMidCrowedLimit(Integer.valueOf(midCrowed[1].trim()));
            }

            if(StrUtil.isNotBlank(baseBusType.getHardCrowedLimit())){
                String[] hardCrowed = baseBusType.getHardCrowedLimit().split(">");
                busTypeCrowedVO.setHardCrowedLimit(Integer.valueOf(hardCrowed[1].trim()));
            }

        } catch (Exception e) {
            log.info("buildBusTypeCrowed error :" + JSON.toJSONString(baseBusType));
        }
        return busTypeCrowedVO;
    }

    public ColorCrowedEnum getColorCrowedEnum(String typeName, Long carryingCapacity) {
        if (Objects.isNull(carryingCapacity)) {
            carryingCapacity = 0L;
        }
        if (!StrUtil.isEmpty(typeName)) {
            BusTypeCrowedVO busTypeCrowedVO = getBusTypeCacheByTypeName(typeName);
            if(Objects.isNull(busTypeCrowedVO) || 0 == busTypeCrowedVO.getMaxLooseLimit()
                    || 0 == busTypeCrowedVO.getMaxLooseLimit() || 0 == busTypeCrowedVO.getMaxMidCrowedLimit()){
                return ColorCrowedEnum.GREEN;
            }
            if (carryingCapacity < busTypeCrowedVO.getMaxLooseLimit()) {
                return ColorCrowedEnum.GREEN;
            }
            if (carryingCapacity < busTypeCrowedVO.getMaxNormalCrowedLimit()) {
                return ColorCrowedEnum.BLUE;
            }
            if (carryingCapacity < busTypeCrowedVO.getMaxMidCrowedLimit()) {
                return ColorCrowedEnum.ORANGE;
            }
            return ColorCrowedEnum.RED;
        }
        //没有找到对应类型
        return ColorCrowedEnum.DEFAULT;
    }

}

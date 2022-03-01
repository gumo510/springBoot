package com.gumo.demo.cache;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 汽车类型
 * </p>
 *
 * @author hy
 * @since 2022-03-01 10:56
 */
public class CarTypeCache {

    /**
     * key: car_type , value: car_type_entity
     */
    private volatile static Map<String, String> carTypeMap;

    public static String get(String key) {
        if (Objects.isNull(carTypeMap) || Objects.isNull(key)) {
            return null;
        }
        return carTypeMap.get(key);
    }

    public synchronized static void setCarTypeMap(Map<String, String> carTypeMap) {
        CarTypeCache.carTypeMap = carTypeMap;
    }

    public synchronized static void clear() {
        if (Objects.nonNull(carTypeMap)) {
            carTypeMap.clear();
        }
    }
}

package com.gumo.demo.constants;

/**
 * @author hy
 * @since 2022-02-21 10:56
 */

public class RedisConstants {

    private RedisConstants() {
    }

    /**
     * 缓存key构建分隔符
     */
    public static final String CACHE_KEY_SEPARATOR = "|";

    /**
     * 默认缓存时长 (秒)
     */
    public static final long REDIS_EXPIRE_IN = 86400L;

    /**
     * 车型缓存
     */
    public static final String BUS_TYPE_CACHE = "bus_type_cache:";

    public static final String BUS_TRAVEL_TIME = "bus_travel_time:";

}

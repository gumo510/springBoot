package com.gumo.demo.utils;


import org.apache.commons.collections.map.LRUMap;

/**
 * 幂等性判断
 * @author gumo
 */
public class IdempotentUtils {

    /**
     * 根据 LRU(Least Recently Used，最近最少使用)算法淘汰数据的 Map 集合，最大容量 100 个
     */
    private static LRUMap reqCache = new LRUMap (100);

    /**
     * 幂等性判断
     * @return
     */
    public static boolean judge(String id, Object lockClass) {
        synchronized (lockClass) {
            // 重复请求判断
            if (reqCache.containsKey(id)) {
                // 重复请求
                System.out.println("请勿重复提交！！！" + id);
                return false;
            }
            // 非重复请求，存储请求 ID
            reqCache.put(id, 1);
        }
        return true;
    }
}

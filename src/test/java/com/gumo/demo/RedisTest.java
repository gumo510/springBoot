package com.gumo.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = DemoApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * https://blog.csdn.net/pengjunlee/article/details/81427894
     *      #### RedisTemplate中定义了对5种数据结构操作
     *
     *      redisTemplate.opsForValue();//操作字符串
     *      redisTemplate.opsForHash();//操作hash
     *      redisTemplate.opsForList();//操作list
     *      redisTemplate.opsForSet();//操作set
     *      redisTemplate.opsForZSet();//操作有序set
     */


    @Test
    public void test() {
        // 判断是否存在
        redisTemplate.hasKey("key");
        // 删除key
        redisTemplate.delete("key");
        // 获取过期时间
        Long expire = redisTemplate.getExpire("key");
        // 设置过期时间
        redisTemplate.expire("key",30, TimeUnit.MINUTES);

    }

    @Test
    public void opsForValue() {
        // 获取key:
        String value = redisTemplate.opsForValue().get("key");
        // 设置key:
        redisTemplate.opsForValue().set("key", value);
        // 设置过期时间:
        redisTemplate.opsForValue().set("key", value,1, TimeUnit.HOURS);

    }


    @Test
    public void opsForHash() {
        HashMap<String, Object> hashMap = Maps.newHashMap();
        // 判断HashKey 是否存在
        redisTemplate.opsForHash().hasKey("redisHash","age");
        // 查询数据
        String value = (String)redisTemplate.opsForHash().get("redisHash", "name");
        // 整个哈希键值对
        Map<Object, Object> redisMap = redisTemplate.opsForHash().entries("redisHash");

        // 单条插入：
        redisTemplate.opsForHash().put("redisHash", "name","tom");
        // 批量插入
        redisTemplate.opsForHash().putAll("redisHash", hashMap);
        // 删除单条数据;
        redisTemplate.opsForHash().delete("redisHash", "name");
        // 删除全部数据;
        redisTemplate.delete("redisHash");
    }


    @Test
    public void opsForList() {
        List<Object> stringOrgList = Lists.newArrayList();
        String[] stringArrays = new String[]{"java","python","c++"};

        // 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部）
        // 返回全部元素
        redisTemplate.opsForList().range("list",0,-1);
        // 返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0。
        redisTemplate.opsForList().size("list");
        // 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
        redisTemplate.opsForList().leftPush("list","java");
        // 插入多个
        redisTemplate.opsForList().leftPushAll("list", stringArrays);
        redisTemplate.opsForList().leftPushAll("listcollection", "1","2","3");

        // 同理插入尾部
        redisTemplate.opsForList().rightPush("list","java");
        redisTemplate.opsForList().rightPushAll("list", stringArrays);

        // 将删除列表中存储的列表中第一次出现的“setValue”。
        redisTemplate.opsForList().remove("list",1,"setValue");
        // 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
        String leftPop = redisTemplate.opsForList().leftPop("list");
        // 移出并获取列表的最后一个元素
        String rightPop = redisTemplate.opsForList().rightPop("list");
    }

    @Test
    public void opsForSet() {
        // Redis的Set是string类型的无序集合。集合成员是唯一的
        String[] strarrays = new String[]{"strarr1","sgtarr2"};

        // 直接添加多个值
        redisTemplate.opsForSet().add("setTest","aaa","bbb");
        // 添加数组
        redisTemplate.opsForSet().add("setTest",strarrays);
        // 无序集合的大小长度
        redisTemplate.opsForSet().size("setTest");
        // 返回集合中所有元素
        redisTemplate.opsForSet().members("setTest");
        // 移除一个或多个
        redisTemplate.opsForSet().remove("setTest",strarrays);

    }

    @Test
    public void redissonLock() {
        RLock lock = redissonClient.getLock("RedissonConst.SYNC_THIRD_ARCHIVE_LOCK + syncThirdArchiveKafkaDTO.getThirdName()");
        try {
            lock.lock();
            // 业务逻辑

            return;
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}

package com.gumo.demo;

import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.mongo.pojo.User;
import com.gumo.demo.mongo.repository.UserRepository;
import com.gumo.demo.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class)
public class MongoTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 自定义查询
     */
    @Test
    public void saveAll() {

        List<User> list = new ArrayList();
        list.add(new User("1","kangkang", 14));
        list.add(new User("2","william", 15));
        list.add(new User("3","jane", 14));
        // 批量保存数据
        userRepository.saveAll(list);

    }

    /**
     * 自定义查询
     */
    @Test
    public void test1() {

        // 保存数据
        userRepository.save(new User("2","test", 18));

        // 自定义查询
        long count = userRepository.count();
        User user = userRepository.findByName("test");
        // 清除数据
        // userRepository.deleteById("1");
        System.out.println(JSONObject.toJSONString(user));
    }

    /**
     *  普通查询条件
     */
    @Test
    public void test2() {
        int page = 1;
        int pageSize = 10;

        Criteria cri = Criteria.where("created").gte("2023-05-10 00:00:00").lte("2030-05-20 23:59:59")
//                .and("name").is("test")
//                .and("age").is(18)
        ;

        Query query = new Query();
        query.addCriteria(cri);
        // 查询数量
        Long count = mongoTemplate.count(query, User.class);
        // 查询排序, 分页
        query.with(Sort.by(Sort.Order.desc("created"))).skip((page - 1) * pageSize).limit(pageSize);
        List<User> users = mongoTemplate.find(query, User.class);
        // 删除
        // mongoTemplate.remove(query, User.class);
        System.out.println(JSONObject.toJSONString(users));
        
    }

    /**
     *  聚合查询 分组,求和,别名,排序,分页
     */
    @Test
    public void test3() {
        int page = 1;
        int pageSize = 10;

        Criteria criteria = Criteria.where("created").gte("2023-05-10 00:00:00").lte("2030-05-20 23:59:59");

        //2 根据工作日name 期进行分组
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//匹配条件
                Aggregation.group("name")//分组字段
                        // as 起别名
                        .first("name").as("name")
                        //3 统计号源数量
                        .count().as("docCount"),
//                        .sum("reservedNumber").as("reservedNumber")
//                        .sum("availableNumber").as("availableNumber"),
                //排序
                Aggregation.sort(Sort.Direction.ASC,"created"),
                //4 实现分页
                Aggregation.skip((page-1)* pageSize),
                Aggregation.limit(pageSize)
        );
        //5 将结果集映射成实体类
        AggregationResults<User> aggregate = mongoTemplate.aggregate(agg, "User", User.class);
        List<User> list = aggregate.getMappedResults();
        System.out.println(JSONObject.toJSONString(list));
    }

    /**
     * 创建 清除 Document
     */
    @Test
    public void collectionExists() {
        if (this.mongoTemplate.collectionExists(User.class)) {
            // 清除表结构
            mongoTemplate.dropCollection(User.class);
            // 新建表结构
            mongoTemplate.createCollection(User.class);
        }
    }

    public static void main(String[] args) {
        long time = DateUtil.strToDate("2022-05-10 00:00:00").getTime();
        System.out.println(time);
    }
}

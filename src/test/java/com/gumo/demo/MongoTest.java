package com.gumo.demo;

import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.mongo.pojo.User;
import com.gumo.demo.mongo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class MongoTest {

    @Autowired
    private UserService userService;

    @Test
    public void test1() {

        userService.saveUser(new User("1","test", 18));
        User user = userService.findUserByName("test");
        System.out.println(JSONObject.toJSONString(user));
    }
}

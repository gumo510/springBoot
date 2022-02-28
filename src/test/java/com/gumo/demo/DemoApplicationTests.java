package com.gumo.demo;

import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.enums.ColorCrowedEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private BusTypeCache busTypeCache;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        System.out.println(colorCrowedEnum.getValue());
    }

}

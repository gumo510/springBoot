package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.mapper.BaseTypeMapper;
import com.gumo.demo.model.dto.CommonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private BusTypeCache busTypeCache;

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    @Test
    public void contextLoads() {
        System.out.println("****************");
    }

    @Test
    public void test1() {
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        System.out.println(colorCrowedEnum.getValue());
    }

    @Test
    public void pageTest() {
        IPage<BaseType> page = new Page<>(2, 3);
//        List<VisitorEncrypt> onlineDrugAuths = visitorEncryptMapper.selectLisTest(page);
        IPage<BaseType> onlineDrugAuths = baseTypeMapper.selectPageTest(page);
        List<BaseType> records = onlineDrugAuths.getRecords();
        System.out.println(JSON.toJSONString(records));
    }

}

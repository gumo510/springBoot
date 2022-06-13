package com.gumo.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.cache.CarTypeCache;
import com.gumo.demo.dto.vo.CommonResult;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.entity.User;
import com.gumo.demo.entity.UserReq;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.service.IUserService;
import com.gumo.demo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BusTypeCache busTypeCache;

    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable int id){
        User user = userService.getById(id);
        return user;
    }

    @PostMapping("getUser/export")
    public CommonResult getUser(){
        CommonResult urlStr = userService.getUserExport();
        return urlStr;
    }

    @GetMapping("getColorCrowed")
    public Integer getColorCrowed(){
        // 测试Redis缓存
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        // 测试启动缓存
        BaseType carTypeDO = JSON.parseObject(Optional.ofNullable(CarTypeCache.get(Optional.ofNullable("比亚迪K10").orElse(""))).orElse("{}"), BaseType.class);
        System.out.println(JSON.toJSON(carTypeDO));
        return colorCrowedEnum.getValue();
    }

//
//    @PostMapping("save/user")
//    public void saveUser(){
//        userService.saveUser();
//    }

}

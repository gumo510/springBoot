package com.gumo.demo.controller;


import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.entity.User;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@RestController
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

    @GetMapping("getColorCrowed")
    public Integer getColorCrowed(){
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        return colorCrowedEnum.getValue();
    }

}

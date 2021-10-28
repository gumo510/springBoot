package com.gumo.demo.controller;


import com.gumo.demo.entity.User;
import com.gumo.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("getUser/{id}")
    public User getUser(@PathVariable int id){
        User user = userService.getById(id);
        return user;
    }

}

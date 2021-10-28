package com.gumo.demo.service.impl;

import com.gumo.demo.entity.User;
import com.gumo.demo.mapper.UserMapper;
import com.gumo.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

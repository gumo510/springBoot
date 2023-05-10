package com.gumo.demo.mongo.service;

import com.gumo.demo.mongo.pojo.User;
import com.gumo.demo.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gumo
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findUserByAgeGreaterThan(Integer age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    // ... 其他操作
}


package com.gumo.demo.mongo.repository;

import com.gumo.demo.mongo.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String name);

    List<User> findByAgeGreaterThan(Integer age);
    // ... 其他操作
}

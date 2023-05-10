package com.gumo.demo.mongo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gumo
 */
@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;

    public User(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}


package com.gumo.demo.mongo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
    private Date created;
    private Date updated;

    public User(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.created = new Date();
    }
}


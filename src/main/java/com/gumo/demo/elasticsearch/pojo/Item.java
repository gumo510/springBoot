package com.gumo.demo.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author
 * @date 2022/07/8 - 14:39
 */
@Document(indexName = "item", shards = 1, replicas = 0)
@Data
public class Item {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")  //设置为text  可以分词
    private String title; //标题

    @Field(type = FieldType.Keyword)   //手动设置为keyword  但同时也就不能分词
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址

    @Field(type = FieldType.Date, name = "create_date", format = DateFormat.custom, pattern="yyyy-MM-dd HH:mm:ss||strict_date_optional_time||epoch_millis")
    private Date createDate;

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
        this.createDate = new Date();
    }
}


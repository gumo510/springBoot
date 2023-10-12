package com.gumo.demo.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * @author gumo
 * @version V1.0
 * @Description
 * @date 2023 09-15 12:03.
 */
@Document(indexName = "documentpojo")
@Data
@Setting(shards=6, replicas = 1)//创建6个索引的分片，索引的一个副本数量
public class DocumentPojo {

    /**
     * 分页后的文档ID
     */
    @Id
    private String id;

    /**
     * 文档ID和知识库中保持一致
     */
    @Field(type = FieldType.Keyword)  //
    private String documentId;
    /**
     * 文档名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")  //设置为text  可以分词
    private String documentName;
    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String content;
    /**
     * 页面
     */
    @Field(type = FieldType.Integer)
    private Integer pageSize;

    /**
     * 页面
     */
    @Field(type = FieldType.Integer)
    private Integer page;

    /**
     * 文件url
     */
    @Field(type = FieldType.Keyword)
    private String url;
    /**
     * 文件格式
     */
    @Field(type = FieldType.Keyword)
    private String type;
    /**
     * 上传时间戳
     */
    @Field(type = FieldType.Long)
    private Date uploadTime;

    @Field(type = FieldType.Integer)
    private Integer isDelete;

    /**
     * 扩展字段
     */
    @Field(type = FieldType.Keyword)
    private String extraField1;

    @Field(type = FieldType.Keyword)
    private String extraField2;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String extraField3;

    public DocumentPojo(String id, String documentId, String documentName, String title, String content,
                        Integer page) {
        this.id = id;
        this.documentId = documentId;
        this.documentName = documentName;
        this.title = title;
        this.content = content;
        this.page = page;
        this.uploadTime = new Date();
    }
}
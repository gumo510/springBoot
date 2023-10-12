package com.gumo.demo.elasticsearch.repository;


import com.gumo.demo.elasticsearch.pojo.DocumentPojo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author gumo
 * @version V1.0
 * @Description
 * @date 2023 09-15 12:03.
 */
@Component
public interface DocumentRepository extends ElasticsearchRepository<DocumentPojo,String> {

}

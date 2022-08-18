package com.gumo.demo.elasticsearch.repository;

import com.gumo.demo.elasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2022/07/8 - 14:39
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    /**
     * 自定义方法
     * 根据价格区间查询
     * @param price1
     * @param price2
     * @return
     */
    List<Item> findByPriceBetween(double price1, double price2);

    /**
     * 自定义方法
     * 根据时间区间查询
     * @param startDate
     * @param endDate
     * @return
     */
    List<Item> findByCreateDateBetween(Date startDate, Date endDate);

    /**
     * 自定义方法
     * 根据标题查询
     * @param title
     * @return
     */
    List<Item> findByTitle(String title);

    /**
     * 自定义方法
     * 根据标题查询
     * @param title
     * @return
     */
    List<Item> findByBrand(String title);
}

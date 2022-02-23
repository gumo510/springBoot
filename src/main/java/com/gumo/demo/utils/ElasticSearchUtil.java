package com.gumo.demo.utils;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ElasticSearchUtil {

    @Autowired
    @Qualifier("elasticSearchClient")
    private RestHighLevelClient restHighLevelClient;

    public List<Address> query(String addr, int pageSize) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("address", addr)
                .operator(Operator.OR)
                .minimumShouldMatch("50%");
        try {
            SearchResponse t_view_building_address = getESPageResults("11111", queryBuilder, null, null, 1, pageSize);
            List<Address> list = processSearchResponse(t_view_building_address, Address.class);
            return list;
        } catch (Exception e) {
            log.error("查询数据失败", e);
        }
        return Collections.EMPTY_LIST;
    }

    private <T> List<T> processSearchResponse(SearchResponse searchResponse, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            list.add(JSON.parseObject(searchHit.getSourceAsString(), tClass));
        }
        return list;
    }

    public SearchResponse getESPageResults(String index, QueryBuilder queryBuilder, String sortName, SortOrder sortOrder, Integer page, Integer pageSize) throws IOException {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        if (pageSize != null) {
            searchSourceBuilder.size(pageSize);
        }
        searchSourceBuilder.from(0);
        if (StringUtils.isNotBlank(sortName) && sortOrder != null) {
            searchSourceBuilder.sort(sortName, sortOrder);
        }
        searchRequest.source(searchSourceBuilder);
        log.info("getESPageResults查询参数：{}", searchRequest.toString());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ES查询出错，错误信息为{}", e);
        }
        return searchResponse;
    }

    public static void main(String[] args) {

    }
}

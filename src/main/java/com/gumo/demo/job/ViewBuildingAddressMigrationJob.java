package com.gumo.demo.job;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.entity.ViewBuildingAddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @created 2022/1/14 9:49
 * @desc 网格区域数据迁移调度任务
 */
@Component
@Slf4j
public class ViewBuildingAddressMigrationJob {

    @Autowired
    @Qualifier("elasticSearchClient")
    private RestHighLevelClient restHighLevelClient;

    @Value("${building_address_index:building_address}")
    private String buildingAddressIndex;

    public void migration() {
        int page = 0;
        int pageSize = 2000;
        while (true) {
            List<ViewBuildingAddress> viewBuildingAddresses = new ArrayList<>();
            for (ViewBuildingAddress viewBuildingAddress : viewBuildingAddresses) {
                saveOrUpdateData(JSON.parseObject(JSON.toJSONString(viewBuildingAddress)), viewBuildingAddress.getBuildingId());
            }
            log.info("插入ES数据成功，插入第{}页，每页数据量:{}", page, pageSize);
            if (viewBuildingAddresses == null || viewBuildingAddresses.size() < pageSize) {
                log.info("数据插入ES完成");
                break;
            }
            page++;
        }
    }

    public void saveOrUpdateData(Map<String, Object> map, String buildingId) {
        //根据buildingId查询es中是否存在，如果不存在就插入，存在就更新
        String docId = queryByBuildingId(buildingId);
        if (StringUtils.isNotBlank(docId)) {
            updateData(map, docId);
        } else {
            insertDate(map);
        }
    }

    private void updateData(Map<String, Object> map, String docId) {
        UpdateRequest updateRequest = new UpdateRequest(buildingAddressIndex, docId);
        updateRequest.doc(map);
        try {
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("更新es数据成功");
    }

    private void insertDate(Map<String, Object> map) {
        IndexRequest buildingAddress = new IndexRequest(buildingAddressIndex);
        buildingAddress.source(map);
        buildingAddress.setRefreshPolicy("wait_for");
        try {
            restHighLevelClient.index(buildingAddress, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("插入es数据成功");
    }

    public String queryByBuildingId(String buildingId) {
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("buildingId", buildingId);
        try {
            SearchResponse results = getESPageResults(StringUtils.isBlank(buildingAddressIndex) ? "building_address" : buildingAddressIndex, queryBuilder, null, null, null, null);
            if (results != null && results.getHits().getHits().length > 0) {
                return results.getHits().getHits()[0].getId();
            }
        } catch (Exception e) {
            log.error("查询ES数据异常", e);
            return null;
        }
        return null;
    }

    public SearchResponse getESPageResults(String index, QueryBuilder queryBuilder, String sortName, SortOrder sortOrder, Integer page, Integer pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        if (pageSize != null) {
            searchSourceBuilder.size(pageSize);
        }
        if (page != null && pageSize != null) {
            searchSourceBuilder.from((page - 1) * pageSize);
        }
        if (StringUtils.isNotBlank(sortName) && sortOrder != null) {
            searchSourceBuilder.sort(sortName, sortOrder);
        }
        searchRequest.source(searchSourceBuilder);
        log.info("getESPageResults查询参数：{}", searchRequest.toString());
        log.info("getESPageResults查询参数：{}", searchRequest.source().toString());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ES查询出错，错误信息为{}", e);
        }
        return searchResponse;
    }
}

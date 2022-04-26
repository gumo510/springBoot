package com.gumo.demo.controller;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ES测试接口
 * Created by gumo on 2022/04/25.
 */
@Api(tags = "ElasticSearchController")
@RestController
@RequestMapping("/elasticsearch")
public class ElasticSearchController {

    @Autowired
    @Qualifier("elasticSearchClient")
    private RestHighLevelClient client;

    /**
     * 测试索引的创建
     */
    @GetMapping("/create")
    @ApiOperation("创建索引")
    void testCreateIndex(@RequestParam("index") String index) throws IOException {
        // 1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 2. 客户端执行请求， 请求后获得响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

        //判断索引是否存在
        // 1. 创建索引请求
        GetIndexRequest response = new GetIndexRequest(index);
        // 2. 客户端执行请求
        boolean exists = client.indices().exists(response, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 测试删除索引
     * @throws IOException
     */
    @GetMapping("/delete")
    @ApiOperation("删除索引")
    void testDeleteIndex(@RequestParam("index") String index) throws IOException {
        // 1。 创建删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        // 2. 客户端执行请求
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(delete));
    }

    /**
     * 测试添加文档
     * @throws IOException
     */
    @GetMapping("/add/document")
    @ApiOperation("添加文档")
    void testAddDocument() throws IOException {
        // 创建对象
        User user = new User("古默", "123456", "gumo");
        // 创建请求
        IndexRequest request = new IndexRequest("zhang_index");
        // 设置规则
        request.id("1");                                //设置文档id
        request.timeout(TimeValue.timeValueSeconds(1)); //设置超时时间
        // 将我们的数据放入请求 使用json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 客户端发送请求
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(indexResponse));

        // 获取文档，判断是否存在
        GetRequest response = new GetRequest("zhang_index","1");
        // 不获取返回的 _source 的上下文了
        response.fetchSourceContext(new FetchSourceContext(false));
        //判断是否存在
        boolean exists = client.exists(response, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 获取文档信息
     * @throws IOException
     */
    @GetMapping("/get/document")
    @ApiOperation("获取文档信息")
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("zhang_index","1");
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(getResponse));
        System.out.println(getResponse.getSourceAsString());
    }

    /**
     * 更新文档操作
     * @throws IOException
     */
    @GetMapping("/update/document")
    @ApiOperation("更新文档操作")
    void updateRequest() throws IOException {
        // 创建修改文档请求
        UpdateRequest updateRequest = new UpdateRequest("zhang_index","1");
        // 设置请求超时时间
        updateRequest.timeout("1s");
        //创建要更新的数据
        User user = new User();
        user.setPassWord("admin1234");
        // 将修改的数据放入到请求当中， XContentType.JSON： 代表请求数据为json格式
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(updateResponse));
        System.out.println(updateResponse.status());
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @GetMapping("/delete/document")
    @ApiOperation("获取文档信息")
    void testDeleteRequest() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("zhang_index","5");
        deleteRequest.timeout("1s");
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     * 批量插入数据
     * @throws IOException
     */
    @GetMapping("/add/document/batch")
    @ApiOperation("批量插入数据")
    void testBigAddRequest() throws IOException {
        // 批量处理
        BulkRequest bulkRequest = new BulkRequest("zhang_index");
        bulkRequest.timeout("10s");

        List<User> userList = new ArrayList<>();
        userList.add(new User("古默", "12", "gumo"));
        userList.add(new User("古默", "123", "gumo"));
        userList.add(new User("古默", "1234", "gumo"));
        userList.add(new User("古默", "12345", "gumo"));
        userList.add(new User("古默", "123456", "gumo"));

        // 批量处理请求
        for (int i = 0; i < userList.size(); i++) {
            // 批量添加，批量更新，批量删除都是在这里操作
            bulkRequest.add(new IndexRequest("zhang_index")
                    .id(""+(i+1))  // 设置文档主键
                    .source(JSON.toJSONString(userList.get(i)), XContentType.JSON)); //放入文档资源
        }

        BulkResponse bulkResponses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponses.status());
        System.out.println(bulkResponses.hasFailures()); // 是否失败， 成功返回false, 失败返回true
        System.out.println(JSON.toJSONString(bulkResponses));
    }

    /**
     * 多功能查询
     * SearchRequest  搜索请求
     * SearchSourceBuilder  条件构造
     * TermQueryBuilder  精确查询
     * BoolQueryBuilder  boolean 查询
     * @throws IOException
     */
    @GetMapping("/search/document")
    @ApiOperation("多功能查询")
    void testSearch() throws IOException {
        // 创建 查询请求
        SearchRequest searchResult = new SearchRequest("zhang_index");
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件，我们可以使用QueryBuilders 工具类来实现
//		QueryBuilder queryBuilder = new QueryBuilder();
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery(); // boolean 值查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","zhanghang1"); // 精确匹配查询
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.from(); //分页
        searchSourceBuilder.size(); // 分页
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置超时时间
        searchResult.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchResult, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("============================");
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

}


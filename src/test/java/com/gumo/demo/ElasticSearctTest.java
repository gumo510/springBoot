package com.gumo.demo;

import com.gumo.demo.elasticsearch.pojo.Item;
import com.gumo.demo.elasticsearch.repository.ItemRepository;
import com.gumo.demo.utils.DateUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.*;

/**
 * @author john
 * @date 2019/12/8 - 14:09
 */
@SpringBootTest
public class ElasticSearctTest {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 测试创建索引
     */
    @Test
    public void testCreate() {
        System.out.println("********");
        IndexOperations indexOperations = restTemplate.indexOps(Item.class);
        // 创建索引，会根据Item类的@Document注解信息来创建
//        restTemplate.createIndex(Item.class);
        indexOperations.create();
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
//        restTemplate.putMapping(Item.class);
        indexOperations.putMapping();

    }

    /**
     * 增加
     */
    @Test
    public void testAdd() {
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    /**
     * 修改(id存在就是修改，否则就是插入)
     */
    @Test
    public void testUpdate() {
        Item item = new Item(6L, "坚果手机R3", "手机",
                "锤子", 2699.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    /**
     * 批量新增
     */
    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(3L, "华为META10", "手机", "华为测试", 4399.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(4L, "华为P40", "手机", "华为手机", 5699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(5L, "华为META40", "手机", "华为手机", 6999.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    /**
     * 删除操作
     */
    @Test
    public void testDelete() {
        itemRepository.deleteById(1L);
    }

    /**
     * 根据id查询
     */
    @Test
    public void testQuery(){
        Optional<Item> optional = itemRepository.findById(2L);
        System.out.println(optional.get());
    }

    /**
     * 查询全部，并按照价格降序排序
     */
    @Test
    public void testFind(){
        // 查询全部，并按照价格降序排序
        Iterable<Item> items = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        items.forEach(item-> System.out.println(item));
    }

    /**
     * 自定义方法，查询分词title
     */
    @Test
    public void testFindLike(){
        // 查询text与keyword 对比
        Iterable<Item> test = itemRepository.findByBrand("华为");
        Iterable<Item> items = itemRepository.findByTitle("华为");
        items.forEach(item-> System.out.println(item));
    }

    /**
     * 自定义方法，按时间查询
     */
    @Test
    public void testFindDate(){
        // 查询创建时间
        Date startDate = DateUtil.strToDate2("2022-08-09 10:00:00");
        Date endDate = DateUtil.strToDate2("2022-08-18 18:00:00");
        Iterable<Item> items = itemRepository.findByCreateDateBetween(startDate, endDate);
        items.forEach(item-> System.out.println(item));
    }


    //虽然基本查询和自定义方法已经很强大了，但是如果是复杂查询（模糊、通配符、词条查询等）就显得力不从心了。此时，我们只能使用原生查询。

    /**
     * QueryBuilders 构建查询条件
     */
//    @Test
//    public void testBaseQuery(){
//        // 词条查询
//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "小米");
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//            .withQuery(QueryBuilders.boolQuery()
//                    .should(QueryBuilders.termQuery("title", "开发"))
//                    .should(QueryBuilders.termQuery("title", "青春"))
//                    .mustNot(QueryBuilders.termQuery("title", "潮头")))
//            .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//            .withPageable(PageRequest.of(0, 50))
//            .build();
//        // 执行查询
//        restTemplate.search((Query) queryBuilder, Item.class);
//        Iterable<Item> items = restTemplate.search(queryBuilder, Item.class);
//        items.forEach(System.out::println);
//    }

    /**
     * NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
     * 分页查询
     */
    @Test
    public void testNativeQuery(){
        // 构建查询条件
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("title", "华为"))  //添加基本的分词查询
                .withFields("message")
                .withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC))    // 排序
                .withPageable(PageRequest.of(0, 2))                     // 分页查询
                .build();

        SearchHits<Item> search = restTemplate.search(searchQuery, Item.class);
        // 打印总条数
        System.out.println(search.getTotalHits());
        //得到查询返回的内容
        List<SearchHit<Item>> searchHits = search.getSearchHits();
        //设置一个最后需要返回的实体类集合
        List<Item> items = new ArrayList<>();
        //遍历返回的内容进行处理
        for(SearchHit<Item> searchHit:searchHits){
            //放到实体类中
            items.add(searchHit.getContent());
        }
        items.forEach(System.out::println);

//        // 执行搜索，获取结果
//        Page<Item> items = itemRepository.searchSimilar(searchQuery, Item.class);
//        // 打印总条数
//        System.out.println(items.getTotalElements());
//        // 打印总页数
//        System.out.println(items.getTotalPages());
//        // 每页大小
//        System.out.println(items.getSize());
//        // 当前页
//        System.out.println(items.getNumber());
    }

    /**
     * 提供更灵活的Api更高的性能可以以很少的代码完成复杂的模糊+分组+分页查询
     */
    @Test
    public void testAggQuery(){
        List<HashMap<String,Long>> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("title","华为"));

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.terms("brand").field("brand").size(10))
                .withPageable(PageRequest.of(0, 1))
                .withQuery(boolQueryBuilder)
                .build();

        MultiBucketsAggregation bucketsAggregation = restTemplate.search(build, Item.class).getAggregations().get("brand");
        for (MultiBucketsAggregation.Bucket bucket : bucketsAggregation.getBuckets()) {
            HashMap<String, Long> hashMap = new HashMap();
            hashMap.put(bucket.getKeyAsString(), bucket.getDocCount());
            list.add(hashMap);
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        list.forEach(System.out::println);
    }

//    /**
//     * 分组
//     */
//    @Test
//    public void testAgg(){
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 不查询任何结果
//        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
//        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
//        queryBuilder.addAggregation(
//                AggregationBuilders.terms("brands").field("brand"));
//        // 2、查询,需要把结果强转为AggregatedPage类型
//        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
//        // 3、解析
//        // 3.1、从结果中取出名为brands的那个聚合，
//        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
//        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
//        // 3.2、获取桶
//        List<StringTerms.Bucket> buckets = agg.getBuckets();
//        // 3.3、遍历
//        for (StringTerms.Bucket bucket : buckets) {
//            // 3.4、获取桶中的key，即品牌名称
//            System.out.println(bucket.getKeyAsString());
//            // 3.5、获取桶中的文档数量
//            System.out.println(bucket.getDocCount());
//        }
//    }
//
//    /**
//     * 嵌套聚合，求平均值
//     */
//    @Test
//    public void testSubAgg(){
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 不查询任何结果
//        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
//        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
//        queryBuilder.addAggregation(
//                AggregationBuilders.terms("brands").field("brand")
//                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
//        );
//        // 2、查询,需要把结果强转为AggregatedPage类型
//        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
//        // 3、解析
//        // 3.1、从结果中取出名为brands的那个聚合，
//        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
//        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
//        // 3.2、获取桶
//        List<StringTerms.Bucket> buckets = agg.getBuckets();
//        // 3.3、遍历
//        for (StringTerms.Bucket bucket : buckets) {
//            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
//            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");
//
//            // 3.6.获取子聚合结果：
//            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
//            System.out.println("平均售价：" + avg.getValue());
//        }
//    }

}



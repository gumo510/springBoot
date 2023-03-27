package com.gumo.demo;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gumo.demo.entity.Menu;
import com.gumo.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    private List<User> list;

    @Test
    public void test0() {

        List<User> userList = new ArrayList<>();
        userList.add(new User(1,"古默", "12", "gumo"));
        userList.add(new User(2,"古默", "123", "gumo"));
        userList.add(new User(3,"古默", "1234", "gumo"));
        userList.add(new User(4,"古默", "12345", "gumo"));
        userList.add(new User(2,"古默", "123456", "gumo"));
//        Collections.sort(userList, (o1, o2) -> (int) (o1.getId() - o2.getId()));
//        List<User> sorted = userList.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        Map<Integer, User> devicePeopleFlowMap = userList.stream().collect(Collectors.toMap(bi -> bi.getId(), bi -> bi ,(b1,b2)->b1));
        System.out.println(devicePeopleFlowMap);
    }

    @Test
    public void test1() {

        // distinctByKey 去重
        List<User> userList = list.stream().filter(distinctByKey(bi -> bi.getUserName())).collect(Collectors.toList());

        // 转换Map 当 Key 冲突时，调用的合并方法
        Map<Integer, User> devicePeopleFlowMap = list.stream().collect(Collectors.toMap(bi -> bi.getId(), bi -> bi ,(b1,b2) -> b1));

        // 获取属性
        List<String> strList = list.stream().map(User::getUserName).distinct().collect(Collectors.toList());

        // List<String> 转 List<Long>
        List<Long> names = strList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        // 过滤转大写
        List<String> upperCase = strList.stream().filter(s -> s.length() > 3).map(s -> s.toUpperCase()).collect(Collectors.toList());

        // 字符串转数组
        List<Integer> typeList = Arrays.stream("1,2,3".split(",")).map(Integer::valueOf).collect(Collectors.toList());
        List<String> joinList = Lists.newArrayList(Splitter.on(",").split("1,2,3"));
        String join = Joiner.on(",").join(typeList);

        // 属性去重数量
        Long sourceIdNum = list.stream().mapToLong(User::getId).distinct().count();

        // 属性求和
        Long personNum = list.stream().mapToLong(User::getId).sum();

        //升序取前三
        List<User> sorted = list.stream().sorted(Comparator.comparing(User::getId)).limit(3).collect(Collectors.toList());

        //降序取前三
        List<User> reversed = list.stream().sorted(Comparator.comparing(User::getId).reversed()).limit(3).collect(Collectors.toList());

        //3.组合,按最新时间排序切割返回
        List<User> retData = Stream.of(sorted, reversed, list)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(User::getId).reversed())
                .limit(3)
                .collect(Collectors.toList());

        // stream group 分组校对
        Map<String, List<User>> collectMap = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.toList()));

        // 分组求和
        Map<String, Long> summingLong = list.stream().collect(Collectors.groupingBy(User::getUserName,Collectors.summingLong(User::getSalary)));

        // 计算名字出现的次数
        Map<String, Long> counting = list.stream().collect(Collectors.groupingBy(User::getUserName,Collectors.counting()));

        // 循环Map 获取Value Set
        List<List<User>> listList = collectMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        // 计算数组最大 最小值
        List<Integer> listInt = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        Integer max = listInt.stream().filter(e -> e != null).max(Comparator.naturalOrder()).orElse(null);
        Integer min = listInt.stream().filter(e -> e != null).min(Comparator.naturalOrder()).orElse(null);

        // 首先过滤属性不为空的,将需要比较的值转出map然后去重,最后取出最大值/最小值
        userList.stream().filter(o -> o.getSalary() != null).map(User::getSalary).distinct().max((e1, e2) -> e1.compareTo(e2)).get();

        // 分组 每组最小值设置标签
        listList.stream().forEach(ls -> ls.stream().min(Comparator.comparing(User::getPassWord)).get().setUserName("最小值"));

        // 过滤其他List元素
        List<User> collect = list.stream().filter(x -> !strList.contains(x.getUserName())).collect(Collectors.toList());

        // 过滤两个list 差值
        List<User> users = list.stream().filter(a -> !collect.stream().map(User::getUserName).collect(Collectors.toList()).contains(a.getUserName())).collect(Collectors.toList());

        // 对缺失值建模
        //String strNull = Optional.ofNullable(req.getEndDate()).orElse("");

        List<String> items = Arrays.asList("北京 天安门", "上海 东方明珠", "厦门 鼓浪屿");
        //flatMap方法 (flatMap需要一个处理嵌套列表的函数，然后将结果串连起来。)
        items.stream().flatMap(item -> Stream.of(item.split(" "))).forEach(System.out::println);
        //结果： 北京 天安门 上海 东方明珠 厦门 鼓浪屿
        // Map方法   (map对列表中的每个元素应用一个函数，返回应用后的元素所组成的列表。)
        items.stream().map(item -> Arrays.stream(item.split(" "))).collect(Collectors.toList()).forEach(System.out::println);
        //java.util.stream.ReferencePipeline$Head@ffaa6af
        //java.util.stream.ReferencePipeline$Head@53ce1329
        //java.util.stream.ReferencePipeline$Head@316bcf94
    }

    @Test
    public void steam() {
        list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.counting()));
        Map<String, Integer> hashMap = new HashMap();
        List<Integer> collect = hashMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        List<User> syncList = Collections.synchronizedList(new ArrayList<>());
        list.parallelStream().forEach(data ->{
            List<User> objects = new ArrayList<>();
            syncList.addAll(objects);
        });

        List<List<User>> partition = ListUtil.partition(list, 500);
        partition.parallelStream().forEach(data ->{
//            clearDate(data);
        });

    }

    @Test
    public void test2() {
        // List parallelStream 聚合
        List<User> sourceFlowStatisticList = Collections.synchronizedList(new ArrayList<>());
        list.parallelStream().forEach(data -> {
            List<User> result = new ArrayList<>();
            sourceFlowStatisticList.addAll(result);
        });
    }

    @Test
    public void test3() {
        // 拆分多个子任务执行
        List<List<User>> subs = ListUtil.partition(list , 500);
        subs.parallelStream().forEach(data -> {
//            clearReturnPersonList(data);
        });
    }

    @Test
    public void test4() {
        // 合并Map 重复key, value相加
        Map<String, BigDecimal> summaryMap = new HashMap();
        List<Map<String, BigDecimal>> travelTimeList = new ArrayList();
        for (Map<String, BigDecimal> decimalMap : travelTimeList) {
            decimalMap.forEach((key, value) -> summaryMap.merge(key, value, BigDecimal::add));
        }
        // Map value求和
        Integer totalCount = summaryMap.values().stream().mapToInt(BigDecimal::intValue).sum();
    }

    /**
     * 可能平常会遇到一些需求，比如构建菜单，构建树形结构，数据库一般就使用父id来表示，为了降低数据库的查询压力，
     * 我们可以使用Java8中的Stream流一次性把数据查出来，
     * 然后通过流式处理，我们一起来看看，代码实现为了实现简单，就模拟查看数据库所有数据到List里面。
     */
    @Test
    public void test5() {

        List<Menu> menus = Arrays.asList(
                new Menu(1,"根节点",0),
                new Menu(2,"子节点1",1),
                new Menu(3,"子节点1.1",2),
                new Menu(4,"子节点1.2",2),
                new Menu(5,"根节点1.3",2),
                new Menu(6,"根节点2",1),
                new Menu(7,"根节点2.1",6),
                new Menu(8,"根节点2.2",6),
                new Menu(9,"根节点2.2.1",7),
                new Menu(10,"根节点2.2.2",7),
                new Menu(11,"根节点3",1),
                new Menu(12,"根节点3.1",11)
        );

        //获取父节点
        List<Menu> collect = menus.stream().filter(m -> m.getParentId() == 0).map(
                (m) -> {
                    m.setChildList(getChildrens(m, menus));
                    return m;
                }
        ).collect(Collectors.toList());
        System.out.println("-------转json输出结果-------");
        System.out.println(JSON.toJSON(collect));
    }

    /**
     * 递归查询子节点
     * @param root  根节点
     * @param all   所有节点
     * @return 根节点信息
     */
    private List<Menu> getChildrens(Menu root, List<Menu> all) {
        List<Menu> children = all.stream().filter(m -> {
            return Objects.equals(m.getParentId(), root.getId());
        }).map(
                (m) -> {
                    m.setChildList(getChildrens(m, all));
                    return m;
                }
        ).collect(Collectors.toList());
        return children;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

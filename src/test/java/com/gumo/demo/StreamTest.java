package com.gumo.demo;

import cn.hutool.core.collection.ListUtil;
import com.gumo.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamTest {

    private List<User> list;

    public static void main(String[] args) {

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

        // 属性去重数量
        Long sourceIdNum = list.stream().mapToLong(User::getId).distinct().count();

        // 属性求和
        Long personNum = list.stream().mapToLong(User::getId).sum();

        //升序取前三
        List<User> sorted = list.stream().sorted(Comparator.comparing(User::getId)).limit(3).collect(Collectors.toList());

        //降序取前三
        List<User> reversed = list.stream().sorted(Comparator.comparing(User::getId).reversed()).limit(3).collect(Collectors.toList());

        // stream group 分组校对
        Map<String, List<User>> collectMap = list.stream().collect(Collectors.groupingBy(User::getUserName, Collectors.toList()));

        // 分组求和
        Map<String, Long> summingLong = list.stream().collect(Collectors.groupingBy(User::getUserName,Collectors.summingLong(User::getSalary)));

        // 计算名字出现的次数
        Map<String, Long> counting = list.stream().collect(Collectors.groupingBy(User::getUserName,Collectors.counting()));

        // 循环Map 获取Value Set
        List<List<User>> listList = collectMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        // 分组 每组最小值设置标签
        listList.stream().forEach(ls -> ls.stream().min(Comparator.comparing(User::getPassWord)).get().setUserName("最小值"));

        // 过滤其他List元素
        List<User> collect = list.stream().filter(x -> !strList.contains(x.getUserName())).collect(Collectors.toList());

        // 过滤两个list 差值
        List<User> users = list.stream().filter(a -> !collect.stream().map(User::getUserName).collect(Collectors.toList()).contains(a.getUserName())).collect(Collectors.toList());

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
            clearDate(data);
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

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}

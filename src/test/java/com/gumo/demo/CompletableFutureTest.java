package com.gumo.demo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.gumo.demo.constants.RedisConstants;
import com.gumo.demo.model.vo.PassAuthPersonDeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 1. newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * 2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * 3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * 4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class CompletableFutureTest {

    @Test
    public void allOfJoin() {
//        //最后抓拍时间
//        CompletableFuture<Map<Long, String>> future1 = CompletableFuture.supplyAsync(() -> {
//            Map<Long, String> snapMap = new HashMap<>();
//            for (DeviceInfo deviceInfo : records) {
//                snapMap.put(deviceInfo.getId(), fillLastSnapDate(deviceInfo));
//            }
//            return snapMap;
//        }, executor);
//        // 获取昨日设备人流
//        CompletableFuture<Map<Long, AreaPeopleFlowVo>> future2 = CompletableFuture.supplyAsync(() -> {
//            String endStr = DateUtil.getYesterday();
//            List<AreaPeopleFlowVo> devicePeopleFlows = areaTempStatisticsMapper.selectSourcePeopleFlowNum(endStr ,collect);
//            Map<Long, AreaPeopleFlowVo> peopleFlowMap = devicePeopleFlows.stream().collect(Collectors.toMap(bi -> bi.getDeviceId(), bi -> bi));
//            return peopleFlowMap;
//        }, executor);
//        // 等待所有线程结束
//        CompletableFuture.allOf(future1, future2).join();
//        Map<Long, String> lastSnapMap = future1.get();
//        Map<Long, AreaPeopleFlowVo> devicePeopleFlowMap = future2.get();
    }

//    @Test
//    public void parallelStream() {
//        // 拆分多个子任务执行
//        List<List<PassAuthPersonDevicePojo>> subs = ListUtil.partition(list , 500);
//        subs.parallelStream().forEach(data -> {
//            buildPassAuthPersonDevicePojoList(list);
//            // 2.同步到ES，实现批量新增
//            passAuthPersonDeviceSupport.saveAll(list);
//            // 3. 记录到同步历史表
//            saveSyncLog(list);
//        });
//    }


//    @Test
//    public void parallelStreamJoin() {
//        // List parallelStream 聚合
//        List<SourceFlowStatistics> sourceFlowStatisticList = Collections.synchronizedList(new ArrayList<>());
//
//        tableNameList.parallelStream().forEach(data -> {
//            List<SourceFlowStatistics> result = eventInfoMapper.getSourceFlowStatistics(GlobalConsts.IFAAS_ANTIEPIDEMIC_DATA_DB, data.getTableName(), start, end);
//            sourceFlowStatisticList.addAll(result);
//        });
//    }

    @Test
    public void test3() throws Exception {
        // 创建异步执行任务:
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Future<Double> cf = executorService.submit(()->{
            System.out.println(Thread.currentThread()+" start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(true){
                throw new RuntimeException("test");
            }else{
                System.out.println(Thread.currentThread()+" exit,time->"+System.currentTimeMillis());
                return 1.2;
            }
        });
        System.out.println("main thread start,time->"+System.currentTimeMillis());
        //等待子任务执行完成,如果已完成则直接返回结果
        //如果执行任务异常，则get方法会把之前捕获的异常重新抛出
        System.out.println("run result->"+cf.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }

    @Test
    public void test2() throws Exception{
//        ForkJoinPool pool=new ForkJoinPool();   //
        // 创建异步执行任务，有返回值
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()-> {
            System.out.println(Thread.currentThread()+" start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(true){
                throw new RuntimeException("test");
            }else{
                System.out.println(Thread.currentThread()+" exit,time->"+System.currentTimeMillis());
                return 1.2;
            }
//        },pool);    //重载版本，可以指定执行异步任务的Executor实现
        });
        System.out.println("main thread start,time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->"+cf.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }

    @Test
    public void test4() throws Exception {
        // 创建异步执行任务，无返回值
        CompletableFuture cf = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread()+" start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(false){
                throw new RuntimeException("test");
            }else{
                System.out.println(Thread.currentThread()+" exit,time->"+System.currentTimeMillis());
            }
        });
        System.out.println("main thread start,time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->"+cf.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }

    /**
     * thenApply / thenApplyAsync
     * thenApply 表示某个任务执行完成后执行的动作，即回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中，测试用例如下：
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        ForkJoinPool pool=new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job1,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job1,time->"+System.currentTimeMillis());
            return 1.2;
        },pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        //thenApply这里实际创建了一个新的CompletableFuture实例
        CompletableFuture<String> cf2=cf.thenApply((result)->{
            System.out.println(Thread.currentThread()+" start job2,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job2,time->"+System.currentTimeMillis());
            return "test:"+result;
        });
        System.out.println("main thread start cf.get(),time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->"+cf.get());
        System.out.println("main thread start cf2.get(),time->"+System.currentTimeMillis());
        System.out.println("run result->"+cf2.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }

    @Test
    private void test6() throws Exception{
        ExecutorService pool = Executors.newFixedThreadPool(100);
        CompletableFuture<Long> logListFuture = CompletableFuture.supplyAsync(() -> getLogList(1), pool);
        CompletableFuture<Long> logTotalFuture = CompletableFuture.supplyAsync(() -> getLogList(2), pool);
        logTotalFuture.get();
        logListFuture.get();
    }

    /**
     *  synchronized 枷锁
     * @param page
     * @return
     */
    private static Long getLogList(int page) {
        try {
            if(page == 3){
                Thread.sleep(10000);
            }else {
                Thread.sleep(2000);
            }
            System.out.println("线程size :" + page);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    //分页线程
    @Test
    private void test7() throws Exception{
        Integer page = 100;
        ExecutorService pool = Executors.newFixedThreadPool(page);
        for (int i = 0; i < page ; i++){
            int finalI = i;
            CompletableFuture.runAsync(() -> getLogList(finalI), pool);
        }
    }

    /**
     * 测试supplyAsync future.get() 多线程阻塞问题  错误实例
     */
    @Test
    private void test9() throws Exception{
        Integer aa[] = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        List<Integer> asList = Arrays.asList(aa);
        long startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(asList.size());
        List<Integer> list = new ArrayList<>();
        for (Integer integer : asList) {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                getLogList(integer);
                return integer;
            }, pool);
            list.add(future.get());
        }
        pool.shutdown();
        System.out.println("time-consuming = " + (System.currentTimeMillis() - startTime));
        System.out.println(list);
    }

    /**
     * 测试supplyAsync future.get() 多线程 使用join
     */
    @Test
    private void test10() throws Exception{
        Integer aa[] = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        List<Integer> asList = Arrays.asList(aa);
        long startTime = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(asList.size());
        List<CompletableFuture<Integer>> futureList = asList.stream().map(integer -> CompletableFuture.supplyAsync(() -> {
            getLogList(integer);
            return integer;
        }, pool)).collect(Collectors.toList());

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        List<Integer> collect = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        //等待全部完成
//        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
//        List<Integer> collect = new ArrayList<>(futureList.size());
//        futureList.forEach(future -> {
//            try {
//                collect.add(future.get());
//            } catch (Exception e) {
//                log.info("aspectFlowListExport_CompletableFuture_error: ",  e);
//            }
//        });
//        pool.shutdown();
        System.out.println("time-consuming = " + (System.currentTimeMillis() - startTime));
        System.out.println(collect);
    }


    /**
     * thenApply 相当于回调函数（callback）
     * whenComplete 返回与此阶段相同的结果或异常的新的CompletionStage，当此阶段完成时，
     * 使用结果（或 null如果没有））和此阶段的异常（或 null如果没有））执行给定的操作。
     *
     * thenCombine 整合两个计算结果
     * @throws Exception
     */
    @Test
    private void test8() throws Exception{

        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i + 1)
                .thenApply(i -> i * i)
                .whenComplete((r, e) -> System.out.println(r));

        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + "Word")
                .thenApply(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture("Java"), (s1, s2) -> s1 + s2)
                .thenAccept(System.out::println);

        System.out.println("************");
    }

    @Test
    private void test00() throws Exception{

        //测试线程执行
        CompletableFuture.supplyAsync(() -> {
            System.out.println("1/0 " + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup().isDaemon());
            return 1;
        }).thenApply( i -> {
            System.out.println(i + " " + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup().isDaemon());
            return 2;
        }).thenApplyAsync( i -> {
            System.out.println(i + " " + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup().isDaemon());
            return 3;
        });
    }

    @Test
    private void join() throws Exception{
//        records.stream().map(dispatchInfo -> CompletableFuture.runAsync(() -> {
//            try {
//                Integer dispatchInterval = getDispatchInterval(finalDate, dispatchInfo.getId(), dispatchInfo.getLineVersionId());
//                List<ArrivalOffsiteVO> arrivalOffsiteVOList = groupMap.get(dispatchInfo.getId());
//                if (arrivalOffsiteVOList == null) {
//                    log.warn("not found arrivalOffsiteVOList by {}", dispatchInfo.getId());
//                    return;
//                }
//                setInBusCount(arrivalOffsiteVOList);
//                saveSitePassengerFlow(dispatchInfo, dispatchInterval, arrivalOffsiteVOList, dayType);
//            } catch (Exception e) {
//                log.error("saveSitePassengerFlow error:", e);
//            }
//        }, threadPoolExecutor)).map(CompletableFuture::join).collect(Collectors.toList());
    }

}

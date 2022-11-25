package com.gumo.demo;

import cn.hutool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
//        });
//        // 获取昨日设备人流
//        CompletableFuture<Map<Long, AreaPeopleFlowVo>> future2 = CompletableFuture.supplyAsync(() -> {
//            String endStr = DateUtil.getYesterday();
//            List<AreaPeopleFlowVo> devicePeopleFlows = areaTempStatisticsMapper.selectSourcePeopleFlowNum(endStr ,collect);
//            Map<Long, AreaPeopleFlowVo> peopleFlowMap = devicePeopleFlows.stream().collect(Collectors.toMap(bi -> bi.getDeviceId(), bi -> bi));
//            return peopleFlowMap;
//        });
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


}

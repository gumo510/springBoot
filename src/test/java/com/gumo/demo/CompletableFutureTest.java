package com.gumo.demo;

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
}

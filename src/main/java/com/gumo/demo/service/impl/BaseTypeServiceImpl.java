package com.gumo.demo.service.impl;

import com.gumo.demo.dto.vo.CorridorTravelTimeVO;
import com.gumo.demo.dto.vo.StatisticsReq;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.mapper.BaseTypeMapper;
import com.gumo.demo.service.IBaseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gumo.demo.utils.CommonUtil;
import com.gumo.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * <p>
 * 类型 服务实现类
 * </p>
 *
 * @author gumo
 * @since 2022-02-28
 */
@Service
@Slf4j
public class BaseTypeServiceImpl extends ServiceImpl<BaseTypeMapper, BaseType> implements IBaseTypeService {

    @Resource
    private BaseTypeMapper baseTypeMapper;

    @Override
    public List<CorridorTravelTimeVO> getLineCorridorTravelTime(StatisticsReq req) {
        //统计天数
        List<String> dayLists = DateUtil.findDayStrList(DateUtil.strToDate(req.getStartDate()), DateUtil.strToDate(req.getEndDate()));
        List<Map<String, BigDecimal>> TravelTimeList = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(dayLists.size());
        dayLists.forEach(day -> {
            String tableName = "tableName" + day.replaceAll("-", "");
            req.setTableName(tableName);
            try {
                CompletableFuture<Map<String, BigDecimal>> future =  CompletableFuture.supplyAsync(() -> {
                    long startTime = System.currentTimeMillis();
                    List<CorridorTravelTimeVO> list = baseTypeMapper.selectLineCorridorTravelTime(req);
                    log.info("[QUERY SQL] getLineCorridorTravelTime:odLineStatisticsMapper.selectLineCorridorTravelTime, time-consuming = {}ms, day = {}", System.currentTimeMillis() - startTime, day);
                    //List转Map
                    Map<String, BigDecimal> corridorTravelTimeMap = list.stream().collect(Collectors.toMap(bi -> bi.getTravelTime(), bi -> bi.getOdNumber()));
                    return corridorTravelTimeMap;
                },pool);
                TravelTimeList.add(future.get());
            } catch (Exception e) {
                log.error("getLineCorridorTravelTime_error!", e);
            }
        });
        List<CorridorTravelTimeVO> travelTimeVOList = buildTravelTimeStatistics(TravelTimeList);
        return travelTimeVOList;
    }

    private List<CorridorTravelTimeVO> buildTravelTimeStatistics(List<Map<String, BigDecimal>> travelTimeList) {
        if(CollectionUtils.isEmpty(travelTimeList)){
            return null;
        }
        //合并Map
        Map<String, BigDecimal> summaryMap = new HashMap();
        for (Map<String, BigDecimal> decimalMap : travelTimeList) {
            decimalMap.forEach((key, value) -> summaryMap.merge(key, value, BigDecimal::add));
        }
        //Map求和
        Integer totalCount = summaryMap.values().stream().mapToInt(BigDecimal::intValue).sum();
        List<CorridorTravelTimeVO> TravelTimeList = new ArrayList<>();
        summaryMap.forEach((key, value) -> {
            CorridorTravelTimeVO travelTimeVO = new CorridorTravelTimeVO();
            travelTimeVO.setTravelTime(key);
            travelTimeVO.setOdNumber(value);
            Double odRate = Double.parseDouble(String.valueOf(value)) / totalCount;
            travelTimeVO.setOdRate(CommonUtil.formatRate(odRate));
            TravelTimeList.add(travelTimeVO);
        });
        return TravelTimeList;
    }
}

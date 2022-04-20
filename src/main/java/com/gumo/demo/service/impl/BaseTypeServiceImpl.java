package com.gumo.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
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

    /**
     *  多线程返回内容缺失问题，使用future.get()获取解决
     * @param param
     * @return
     * @throws Exception
     */
/*    public String aspectFlowListExport(AspectFlowReq param) throws Exception {
        //1.获取数据
        List<MaxAspectFlowDO> aspectFlowDOS = this.baseMapper.aspectFlowList(param);
        log.info("aspectFlowListExport_size: ",  aspectFlowDOS.size());
        if (CollectionUtils.isEmpty(aspectFlowDOS)) {
            return null;
        }
        List<CompletableFuture<MaxAspectFlowExportDO>> futureList = aspectFlowDOS.stream().
                map(aspectFlowDO -> CompletableFuture.supplyAsync(() -> {
                    //do something async
                    MaxAspectFlowExportDO aspectFlowExportDO = new MaxAspectFlowExportDO();
                    BeanUtils.copyProperties(aspectFlowDO, aspectFlowExportDO);
                    return aspectFlowExportDO;
                }, taskExecutor))
                .collect(Collectors.toList());
        //等待全部完成
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        List<MaxAspectFlowExportDO> aspectFlowExportDOList = new ArrayList<>(aspectFlowDOS.size());
        futureList.forEach(future -> {
            try {
                aspectFlowExportDOList.add(future.get());
            } catch (Exception e) {
                log.info("aspectFlowListExport_CompletableFuture_error: ",  e);
            }
        });
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EasyExcel.write(byteArrayOutputStream, MaxAspectFlowExportDO.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet().doWrite(aspectFlowExportDOList);
        byte[] excel = byteArrayOutputStream.toByteArray();
        JSONObject fileParam = fileService.uploadByByte(excel, "xlsx");
        if (fileParam != null) {
            return fileParam.getString("fileUrl");
        }
        return null;
    }*/

}

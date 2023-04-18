package com.gumo.demo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gumo.demo.constants.RedisConstants;
import com.gumo.demo.enums.PassAuthPersonTypeEnum;
import com.gumo.demo.model.vo.CorridorTravelTimeVO;
import com.gumo.demo.model.req.StatisticsReq;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.mapper.BaseTypeMapper;
import com.gumo.demo.model.vo.PassAuthPersonDeviceVO;
import com.gumo.demo.service.IBaseTypeService;
import com.gumo.demo.utils.CommonUtil;
import com.gumo.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${travelTime.cache.timeout:12}")
    private Integer redisCacheTimeout;

    @Value("${person.auth.clear.useless.max.number:100000}")
    public Integer clearUselessMaxNumber;

    @Value("${person.auth.clear.useless.size:1000}")
    public Integer clearUselessSize;

    private static Integer corePoolSize;

    private static Integer maxPoolSize;

    private static Integer queueSize;

    private static ThreadPoolTaskExecutor executor;

    @Value("${wechat.send.pool.core:2}")
    public void setCorePoolSize(Integer corePoolSize) {
        BaseTypeServiceImpl.corePoolSize = corePoolSize;
    }

    @Value("${wechat.send.pool.max:8}")
    public void setMaxPoolSize(Integer maxPoolSize) {
        BaseTypeServiceImpl.maxPoolSize = maxPoolSize;
    }

    @Value("${wechat.send.pool.queue:1000}")
    public void setQueueSize(Integer queueSize) {
        BaseTypeServiceImpl.queueSize = queueSize;
    }

    @PostConstruct
    public void init() {
        executor = new ThreadPoolTaskExecutor();
        //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        //queueCapacity 线程池所使用的缓冲队列
        executor.setQueueCapacity(queueSize);
        // 设置默认线程名称
        executor.setThreadNamePrefix("Wechat-Message-Push-");
        executor.initialize();
    }

    @Override
    public List<CorridorTravelTimeVO> getLineCorridorTravelTime(StatisticsReq req) {
        String cacheKey = RedisConstants.BUS_TRAVEL_TIME  + Optional.ofNullable(req.getStartDate()).orElse("") + "-" +
                Optional.ofNullable(req.getEndDate()).orElse("");
        if (Objects.nonNull(req.getCancelCache()) && !req.getCancelCache()) {
            //1.根据请求参数作为key查询缓存，若存在直接返回
            String travelTimeJson = redisTemplate.opsForValue().get(cacheKey);
            if (Objects.nonNull(travelTimeJson)) {
                return JSON.parseArray(travelTimeJson, CorridorTravelTimeVO.class);
            }
        }
        //统计天数
        List<String> dayLists = DateUtil.findDayStrList(DateUtil.strToDate(req.getStartDate()), DateUtil.strToDate(req.getEndDate()));
        ExecutorService pool = Executors.newFixedThreadPool(dayLists.size());
        List<CompletableFuture<Map<String, BigDecimal>>> futureList = dayLists.stream().map(day -> CompletableFuture.supplyAsync(() -> {
            StatisticsReq futureReq = new StatisticsReq();
            String tableName = "tableName" + day.replaceAll("-", "");
            futureReq.setTableName(tableName);
            long startTime = System.currentTimeMillis();
            List<CorridorTravelTimeVO> list = baseTypeMapper.selectLineCorridorTravelTime(futureReq);
            log.info("[QUERY SQL] getLineCorridorTravelTime:odLineStatisticsMapper.selectLineCorridorTravelTime, time-consuming = {}ms, day = {}", System.currentTimeMillis() - startTime, day);
            Map<String, BigDecimal> corridorTravelTimeMap = list.stream().collect(Collectors.toMap(bi -> bi.getTravelTime(), bi -> bi.getOdNumber()));
            return corridorTravelTimeMap;
        }, pool)).collect(Collectors.toList());
        //线程聚合等待
//        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        List<Map<String, BigDecimal>> travelTimeList = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        List<CorridorTravelTimeVO> travelTimeVOList = buildTravelTimeStatistics(travelTimeList);
        pool.shutdown();
        //存入缓存
        redisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(travelTimeVOList), redisCacheTimeout, TimeUnit.HOURS);
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
        List<CorridorTravelTimeVO> corridorTravelTimeList = new ArrayList<>();
        summaryMap.forEach((key, value) -> {
            CorridorTravelTimeVO travelTimeVO = new CorridorTravelTimeVO();
            travelTimeVO.setTravelTime(key);
            travelTimeVO.setOdNumber(value);
            Double odRate = Double.parseDouble(String.valueOf(value)) / totalCount;
            travelTimeVO.setOdRate(CommonUtil.formatRate(odRate));
            corridorTravelTimeList.add(travelTimeVO);
        });
        return corridorTravelTimeList;
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

    /**
     * 清理大表数据
     * @param
     * @return
     */
    public void clearUselessPersonAuthority() {
        log.info("clearUselessPersonAuthority_start... ");
        long start = System.currentTimeMillis();
        PassAuthPersonDeviceVO authPersonType1 = PassAuthPersonDeviceVO.builder().pageSize(clearUselessSize).status(0).type(1).authorizationType(1).personType(2).build();
        PassAuthPersonDeviceVO authPersonType2 = PassAuthPersonDeviceVO.builder().pageSize(clearUselessSize).status(0).type(2).build();
        PassAuthPersonDeviceVO authPersonType3 = PassAuthPersonDeviceVO.builder().pageSize(clearUselessSize).status(0).type(3).build();
        //
        //配置执行类型
        List<Integer> typeList = Arrays.stream("1,2,3".split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<CompletableFuture<Integer>> CompletableFutureList = new ArrayList<>();
        for (Integer type : typeList) {
            if(PassAuthPersonTypeEnum.ADD.getValue().equals(type)){
                CompletableFuture<Integer> CompletableFuture1 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType1), executor);
                CompletableFutureList.add(CompletableFuture1);
            }else if(PassAuthPersonTypeEnum.UPDATE.getValue().equals(type)){
                CompletableFuture<Integer> CompletableFuture2 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType2), executor);
                CompletableFutureList.add(CompletableFuture2);
            }else if(PassAuthPersonTypeEnum.DELETE.getValue().equals(type)){
                CompletableFuture<Integer> CompletableFuture3 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType3), executor);
                CompletableFutureList.add(CompletableFuture3);
            }
        }
        CompletableFuture.allOf(CompletableFutureList.toArray(new CompletableFuture[0])).join();
//        CompletableFuture<Integer> CompletableFuture1 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType1), executor);
//        CompletableFuture<Integer> CompletableFuture2 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType2), executor);
//        CompletableFuture<Integer> CompletableFuture3 = CompletableFuture.supplyAsync(() -> syncClearUselessPersonAuth(authPersonType3), executor);
//        CompletableFuture.allOf(CompletableFuture1, CompletableFuture2, CompletableFuture3).join();

        log.info("clearUselessPersonAuthority_end 耗时{}", System.currentTimeMillis() - start);
    }

    private Integer syncClearUselessPersonAuth(PassAuthPersonDeviceVO authPersonVo) {
        Integer clearMaxNum = 0;
        long midStart = System.currentTimeMillis();
        try {
            //1.查询缓存偏移量
            String value = redisTemplate.opsForValue().get(RedisConstants.CLEAR_USELESS_TYPE + authPersonVo.getType());
            Long offset = StringUtils.isEmpty(value) ? 0L :Long.valueOf(value);
            while (clearMaxNum < clearUselessMaxNumber) {
                //2.查询无用数据
                authPersonVo.setOffset(offset);
                List<Long> ids = new ArrayList<>();  //authorityMapper.selectUselessPersonAuth(authPersonVo);
                if (CollectionUtils.isEmpty(ids)) {
                    redisTemplate.opsForValue().set(RedisConstants.CLEAR_USELESS_TYPE + authPersonVo.getType(), String.valueOf(0));
                    log.info("clearUselessPersonAuthority 记录类型{} 清理完成", PassAuthPersonTypeEnum.getName(authPersonVo.getType()));
                    break;
                }
                //3.清理数据
                List<List<Long>> partition = ListUtil.partition(ids, 500);
                partition.parallelStream().forEach(data ->{
//                    authorityMapper.deleteAuthByIds(ids);
                });
                clearMaxNum += ids.size();
                offset = Collections.max(ids);
                log.info("clearUselessPersonAuthority 记录类型{} 已清理{}条, taskTime:{}ms", PassAuthPersonTypeEnum.getName(authPersonVo.getType()), clearMaxNum, System.currentTimeMillis() - midStart);
            }
            redisTemplate.opsForValue().set(RedisConstants.CLEAR_USELESS_TYPE + authPersonVo.getType(), String.valueOf(offset));
        } catch (Exception e) {
            log.error("clearUselessPersonAuthority_error 记录类型{} 清理异常： {}", PassAuthPersonTypeEnum.getName(authPersonVo.getType()), e.getMessage());
        }
        return clearMaxNum;
    }
}

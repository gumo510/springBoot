package com.gumo.demo.hik.service;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gumo.demo.hik.config.HikVersionConfig;
import com.gumo.demo.hik.model.req.HikEventSubReq;
import com.gumo.demo.hik.model.resp.HikCameraGetResp;
import com.gumo.demo.hik.model.resp.HikCommonResult;
import com.gumo.demo.hik.model.resp.HikEventSubViewResp;
import com.gumo.demo.hik.util.HikVersionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 海康接口服务类
 */
@Service
@Slf4j
public class HikService {

    /**
     * 查询在线设备URL
     */
    @Value("${hikang.isc.camera.get.url:api/nms/v1/online/camera/get}")
    private String deviceResourceByPageUrl;
    /**
     * 查询事件订阅信息URL
     */
    @Value("${hikang.isc.eventSubscriptionView.url:api/eventService/v1/eventSubscriptionView}")
    private String eventSubscriptionViewUrl;
    /**
     * 按事件类型订阅事件URL
     */
    @Value("${hikang.isc.eventSubscription.url:api/eventService/v1/eventSubscriptionByEventTypes}")
    private String eventSubscriptionByEventTypesUrl;
    /**
     * 按事件类型取消订阅URL
     */
    @Value("${hikang.isc.eventUnSubscription.url:api/eventService/v1/eventUnSubscriptionByEventTypes}")
    private String eventUnSubscriptionByEventTypesUrl;
    /**
     * 获取联动事件列表URL
     */
    @Value("${hikang.isc.events.search.url:api/els/v1/events/search}")
    private String eventsSearchUrl;
    /**
     * 获取监控点预览取流URLv2
     */
    @Value("${hikang.isc.cameras.previewURLs.url:api/video/v2/cameras/previewURLs}")
    private String previewUrl;
    /**
     * 获取监控点回放取流URLv2
     */
    @Value("${hikang.isc.cameras.playbackURLs.url:api/video/v2/cameras/playbackURLs}")
    private String playbackUrl;

    @Autowired
    private HikVersionConfig hikVersionConfig;

    /**
     * 分页获取监控点资源在线信息
     * @param requestStr
     * @return
     */
    public HikCommonResult<HikCameraGetResp> onlineCameraGet(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(deviceResourceByPageUrl, requestStr);
        HikCommonResult<HikCameraGetResp> resultDto = JSONObject.parseObject(hikDataStr, new TypeReference<HikCommonResult<HikCameraGetResp>>(){});
        return resultDto;
    }

    /**
     * 查询事件订阅信息
     * @param requestStr
     * @return
     */
    public HikCommonResult<HikEventSubViewResp> eventSubscriptionView(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(eventSubscriptionViewUrl, requestStr);
        HikCommonResult<HikEventSubViewResp> resultDto = JSONObject.parseObject(hikDataStr, new TypeReference<HikCommonResult<HikEventSubViewResp>>(){});
        return resultDto;
    }

    /**
     * 按事件类型订阅事件
     * @param requestStr
     * @return
     */
    public HikCommonResult eventSubscriptionByEventTypes(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(eventSubscriptionByEventTypesUrl, requestStr);
        HikCommonResult resultDto = JSONObject.parseObject(hikDataStr, HikCommonResult.class);
        return resultDto;
    }

    /**
     * 按事件类型取消订阅
     * @param requestStr
     * @return
     */
    public HikCommonResult eventUnSubscriptionByEventTypes(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(eventUnSubscriptionByEventTypesUrl, requestStr);
        HikCommonResult resultDto = JSONObject.parseObject(hikDataStr, HikCommonResult.class);
        return resultDto;
    }

    /**
     * 获取联动事件列表
     * @param requestStr
     * @return
     */
    public HikCommonResult eventsSearch(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(eventsSearchUrl, requestStr);
        HikCommonResult resultDto = JSONObject.parseObject(hikDataStr, HikCommonResult.class);
        return resultDto;
    }

    /**
     * 获取监控点预览取流URLv2
     * @param requestStr
     * @return
     */
    public HikCommonResult previewURLs(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(previewUrl, requestStr);
        HikCommonResult resultDto = JSONObject.parseObject(hikDataStr, HikCommonResult.class);
        return resultDto;
    }

    /**
     * 获取监控点回放取流URLv2
     * @param requestStr
     * @return
     */
    public HikCommonResult playbackURLs(String requestStr) {
        String hikDataStr = HikVersionUtil.getHikDataByPost(playbackUrl, requestStr);
        HikCommonResult resultDto = JSONObject.parseObject(hikDataStr, HikCommonResult.class);
        return resultDto;
    }

    // ... 其他操作
    public void subscribeEvent() {
        try {
            // 查询事件订阅信息
            HikCommonResult<HikEventSubViewResp> resultDto = this.eventSubscriptionView(null);
            if(Objects.nonNull(resultDto) && "0".equals(resultDto.getCode())){
                if(Objects.nonNull(resultDto.getData())){
                    List<HikEventSubViewResp.Detail> details = resultDto.getData().getDetail().stream()
                            .filter(detail -> Objects.nonNull(detail) && detail.getEventDest().equals(hikVersionConfig.getEventDest()))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isEmpty(details)){
                        //订阅事件
                        HikEventSubReq eventSubReq = new HikEventSubReq(hikVersionConfig.getEventTypeStr(),hikVersionConfig.getEventDest(),0,"2");
                        HikCommonResult eventSubDto = this.eventSubscriptionByEventTypes(JSONObject.toJSONString(eventSubReq));
                        log.info("订阅高空抛物事件 eventSubReq: {}, eventSubDto: {}", JSONObject.toJSONString(eventSubReq), JSONObject.toJSONString(eventSubDto));
                    }
                }
            }
        } catch (Exception e) {
            log.error("订阅高空抛物事件失败", e);
        }
    }
}


package com.gumo.demo.job;

import com.gumo.demo.constants.RedisConstants;
import com.gumo.demo.entity.CameraInfo;
import com.gumo.demo.enums.CameraOnlineEnum;
import com.gumo.demo.enums.OperateTypeEnum;
import com.gumo.demo.kafka.producer.KafkaSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author gumo
 * 心跳进行定时（5分钟一次）任务判断设备是否在线和最后在线时间；
 */
@Slf4j
@Component
public class SyncOfflineTimeJob {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaSendService kafkaSendService;

//    @Scheduled(cron = "${syncOfflineTime.cron:0 0/5 * * * ?}")
    public void updateOfflineTime() {
        log.info("updateOfflineTime_start...");
//        List<CameraInfo> onlineCameraInfos = cameraInfosMapper.selectAllCameraInfo(CameraOnlineEnum.ONLINE.getValue());
//        List<CameraInfo> offlineCameraInfos = cameraInfosMapper.selectAllCameraInfo(CameraOnlineEnum.OFFLINE.getValue());
        List<CameraInfo> onlineCameraInfos = new ArrayList<>();
        List<CameraInfo> offlineCameraInfos = new ArrayList<>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(CameraOnlineEnum.values().length, CameraOnlineEnum.values().length,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        CompletableFuture.runAsync(() -> {
            for (CameraInfo cameraInfo : onlineCameraInfos) {
                try {
                    Object value = redisTemplate.opsForValue().get(RedisConstants.CAMERA_SDC_TOKEN + cameraInfo.getCameraCode());
                    if (Objects.isNull(value)) {
                        cameraInfo.setLastOfflineTime(new Date());
                        cameraInfo.setOnline(CameraOnlineEnum.OFFLINE.getValue());
                        kafkaSendService.sendKafkaCameraOperate(cameraInfo, OperateTypeEnum.UPDATE.getValue());
                    }
                } catch (Exception e) {
                    log.error("updateOfflineTime_online_error cameraId:{}, e:{}", cameraInfo.getId(), e);
                }
            }
        }, pool);

        CompletableFuture.runAsync(() -> {
            for (CameraInfo cameraInfo : offlineCameraInfos) {
                try {
                    Object value = redisTemplate.opsForValue().get(RedisConstants.CAMERA_SDC_TOKEN + cameraInfo.getCameraCode());
                    if (Objects.nonNull(value)) {
                        cameraInfo.setLastOfflineTime(null);
                        cameraInfo.setOnline(CameraOnlineEnum.ONLINE.getValue());
                        kafkaSendService.sendKafkaCameraOperate(cameraInfo, OperateTypeEnum.UPDATE.getValue());
                    }
                } catch (Exception e) {
                    log.error("updateOfflineTime_offline_error cameraId:{}, e:{}", cameraInfo.getId(), e);
                }
            }
        }, pool);
    }

}

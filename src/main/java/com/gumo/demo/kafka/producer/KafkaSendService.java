package com.gumo.demo.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.gumo.demo.constants.KafkaConstants;
import com.gumo.demo.dto.vo.CameraOperateDTO;
import com.gumo.demo.dto.vo.UserOperateDTO;
import com.gumo.demo.entity.CameraInfo;
import com.gumo.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author gumo
 */
@Slf4j
@Component
public class KafkaSendService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendKafkaAreaOperate(User user, Integer operateType) {
        try {
            UserOperateDTO userOperateDTO = new UserOperateDTO();
            BeanUtils.copyProperties(user, userOperateDTO);
            userOperateDTO.setOperateType(operateType);
            userOperateDTO.setOperateDate(new Date());
            kafkaTemplate.send(KafkaConstants.OPERATE_SYNC_USER_INFO, JSON.toJSONString(userOperateDTO));
        } catch (BeansException e) {
            log.error("sendKafkaAreaOperate send kafka error userId: {}, exception: {}",user.getId(), e);
        }
    }

    public void sendKafkaCameraOperate(CameraInfo cameraInfo, Integer operateType) {
        try {
            CameraOperateDTO cameraOperateDTO = new CameraOperateDTO();
            BeanUtils.copyProperties(cameraInfo, cameraOperateDTO);
            cameraOperateDTO.setOperateType(operateType);
            cameraOperateDTO.setOperateDate(new Date());
            kafkaTemplate.send(KafkaConstants.OPERATE_SYNC_CAMERA_INFO, JSON.toJSONString(cameraOperateDTO));
        } catch (BeansException e) {
            log.error("operate_sync_camera_info send kafka error cameraId: {}, exception: {}",cameraInfo.getId(), e);
        }
    }
}

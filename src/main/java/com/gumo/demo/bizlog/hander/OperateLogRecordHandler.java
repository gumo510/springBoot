package com.gumo.demo.bizlog.hander;

import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.config.CommonThreadPool;
import com.gumo.demo.entity.OperateLog;
import com.gumo.demo.mapper.OperateLogMapper;
import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 获取操作人信息配置
 * </p>
 *
 * @author Evan
 * @since 2024-03-19 16:10
 */
@Service
@Slf4j
public class OperateLogRecordHandler implements ILogRecordService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void record(LogRecord logRecord) {
        try {
            if (Objects.isNull(logRecord) || !StringUtils.hasText(logRecord.getAction())) {
                return;
            }

            OperateLog operateLog =  buildOperateLog(logRecord);
            //发送kafka 消息主题，异步存储到数据库
//            kafkaTemplate.send(KafkaConstants.OPERATE_LOG_INFO, JSON.toJSONString(operateLog));

            // 异步写入日志
            CommonThreadPool.EXECUTOR_SERVICE.execute(()->{
                operateLogMapper.insert(operateLog);
            });
            log.info("操作日志：{}",JSONObject.toJSONString(operateLog));
        } catch (Exception e) {
            log.error("操作日志处理异常!LogRecord:{}", JSONObject.toJSONString(logRecord), e);
        }
    }

    private OperateLog buildOperateLog(LogRecord logRecord) {
        OperateLog operateLog = new OperateLog();
        // 用户信息根据userId获取
        operateLog.setUserId(Long.parseLong(logRecord.getOperator()));
        operateLog.setUserName("superuser");
        operateLog.setUserAccount("superuser");
        operateLog.setUserRole("superuser");
        operateLog.setUserOrganization("全部");
        operateLog.setOperatePlatform("WEB");
        operateLog.setOperateContent(logRecord.getAction());
        operateLog.setOperateTime(logRecord.getCreateTime());
        operateLog.setOperateType(Integer.parseInt(logRecord.getBizNo()));
//            operateLog.setOperateIp(GeoIpUtil.getIpAddress());

        return operateLog;
    }

    @Override
    public List<LogRecord> queryLog(String bizKey) {
        return null;
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo) {
        return null;
    }
}

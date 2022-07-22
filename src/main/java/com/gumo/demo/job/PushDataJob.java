package com.gumo.demo.job;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gumo.demo.constants.GlobalConstants;
import com.gumo.demo.mapper.DeviceInfoMapper;
import com.gumo.demo.utils.DateUtil;
import com.gumo.demo.webserver.DeviceInfoRow;
import com.gumo.demo.webserver.MathInstituteWebServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class PushDataJob {

    @Value("${push.data.size:1000}")
    private Integer pushDataSize;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private MathInstituteWebServiceClient mathInstituteWebServiceClient;

    public void pushDeviceInfo(String pushDate) {
        //1.分页查询推送数据
        log.info("JinJiangPushDataJob_pushDeviceInfo：statDate:{}", pushDate);
        String startTime = pushDate + " 00:00:00";
        String endTime = DateUtil.getTodyDateStr() + " 00:00:00";
        Integer page = BigDecimal.ONE.intValue();
        while (true) {
            IPage<DeviceInfoRow> logPage = new Page<>(page, pushDataSize);
            List<DeviceInfoRow> deviceInfos = deviceInfoMapper.queryDeviceInfoRows(logPage, startTime, endTime);
            if (CollectionUtils.isEmpty(deviceInfos)) {
                break;
            }
            mathInstituteWebServiceClient.pushRow(GlobalConstants.CATALOG_ID_9, deviceInfos);
            log.info("JinJiangPushDataJob_pushDeviceInfo total : {}, startTime: {}, endTime: {}", deviceInfos.size(), startTime, endTime);
            page = page + 1;
        }
    }
}

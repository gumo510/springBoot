package com.gumo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gumo.demo.entity.DeviceInfo;
import com.gumo.demo.webserver.DeviceInfoRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author intellif
 * @since 2021-10-22
 */
@Mapper
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    List<DeviceInfoRow> queryDeviceInfoRows(IPage<DeviceInfoRow> logPage, @Param("startTime") String startTime, @Param("endTime") String endTime);

}

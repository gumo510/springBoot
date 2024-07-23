package com.gumo.demo.mapper;

import com.gumo.demo.entity.OperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author gumo
 * @since 2024-07-22
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}

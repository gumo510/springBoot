package com.gumo.demo.service.impl;

import com.gumo.demo.entity.OperateLog;
import com.gumo.demo.mapper.OperateLogMapper;
import com.gumo.demo.service.IOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author gumo
 * @since 2024-07-22
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}

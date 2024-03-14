package com.gumo.demo.service.impl;

import com.gumo.demo.entity.ResultCodeEnum;
import com.gumo.demo.mapper.ResultCodeEnumMapper;
import com.gumo.demo.service.IResultCodeEnumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 枚举类状态码 服务实现类
 * </p>
 *
 * @author intellif
 * @since 2024-03-05
 */
@Service
public class ResultCodeEnumServiceImpl extends ServiceImpl<ResultCodeEnumMapper, ResultCodeEnum> implements IResultCodeEnumService {

}

package com.gumo.demo.mapper;

import com.gumo.demo.entity.BaseType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 类型 Mapper 接口
 * </p>
 *
 * @author gumo
 * @since 2022-02-28
 */
@Mapper
public interface BaseTypeMapper extends BaseMapper<BaseType> {

    List<BaseType> selectBaseBusTypes();
}

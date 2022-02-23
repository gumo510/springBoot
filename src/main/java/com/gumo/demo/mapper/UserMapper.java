package com.gumo.demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gumo.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@Mapper
//@DS("test")
public interface UserMapper extends BaseMapper<User> {

}

package com.gumo.demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gumo.demo.entity.MenuExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单扩展表 Mapper 接口
 * </p>
 *
 * @author gumo
 * @since 2023-06-21
 */
@Mapper
@DS("test")
public interface MenuExtMapper extends BaseMapper<MenuExt> {

}

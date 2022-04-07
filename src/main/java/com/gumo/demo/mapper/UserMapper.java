package com.gumo.demo.mapper;

import com.gumo.demo.dto.vo.UserVO;
import com.gumo.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    List<UserVO> queryUserList();
}

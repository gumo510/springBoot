package com.gumo.demo.mapper;

import com.gumo.demo.model.vo.UserDeviceVO;
import com.gumo.demo.model.vo.UserVO;
import com.gumo.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    UserDeviceVO getUserDevice(@Param("userName") String login, @Param("passWord") String password);

    void updateKeyWordById(@Param("user") User user);
}

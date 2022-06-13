package com.gumo.demo.service;

import com.gumo.demo.dto.vo.CommonResult;
import com.gumo.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
public interface IUserService extends IService<User> {

    CommonResult getUserExport();
}

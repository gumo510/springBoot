package com.gumo.demo.service.impl;

import com.gumo.demo.entity.Menu;
import com.gumo.demo.mapper.MenuMapper;
import com.gumo.demo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author gumo
 * @since 2023-06-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}

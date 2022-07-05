package com.gumo.demo.service.impl;

import com.gumo.demo.entity.Dictionary;
import com.gumo.demo.mapper.DictionaryMapper;
import com.gumo.demo.service.IDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gumo
 * @since 2022-06-24
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}

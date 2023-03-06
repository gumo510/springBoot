package com.gumo.demo.service;

import com.gumo.demo.model.vo.CorridorTravelTimeVO;
import com.gumo.demo.model.req.StatisticsReq;
import com.gumo.demo.entity.BaseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 类型 服务类
 * </p>
 *
 * @author gumo
 * @since 2022-02-28
 */
public interface IBaseTypeService extends IService<BaseType> {

    List<CorridorTravelTimeVO> getLineCorridorTravelTime(StatisticsReq req);

}

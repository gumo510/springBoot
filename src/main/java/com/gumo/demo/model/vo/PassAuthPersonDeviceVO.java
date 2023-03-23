package com.gumo.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gumo
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassAuthPersonDeviceVO {

    /**
     *  页码
     */
    private Integer page;
    /**
     *  页数
     */
    private Integer pageSize;
    /**
     *  记录类型,1新增,2删除,3修改
     */
    private Integer type;
    /**
     * 执行状态,具体看情况处理,默认-1,执行成功0
     */
    private Integer status;
    /**
     * 是否是永久授权，0永久，1临时
     */
    private Integer authorizationType;
    /**
     * 授权人员类型,1普通人员,2访客
     */
    private Integer personType;
    /**
     *  偏移量
     */
    private Long offset;
    /**
     *  开始时间
     */
    private String beginTime;
    /**
     *  结束时间
     */
    private String endTime;
}

//    PassAuthPersonDeviceVo passVo = PassAuthPersonDeviceVo.builder()
//            .page(page).pageSize(pageSize).maxAuthorityId(maxAuthorityId)
//            .build();
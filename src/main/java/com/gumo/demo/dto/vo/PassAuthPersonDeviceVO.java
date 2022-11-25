package com.gumo.demo.dto.vo;

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
     *  最大授权Id
     */
    private Long maxAuthorityId;
    /**
     *  记录类型,1新增,2删除,3修改
     */
    private Integer type;
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
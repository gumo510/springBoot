package com.gumo.demo.entity;

import lombok.Data;

/**
 * @author gumo
 * 行政区域Excel数据
 * https://juejin.cn/post/6844903975196557320
 *
 *  id	登记ID	创建时间	修改时间	推送状态	推送报文	描述	推送次数
 */
@Data
public class ExcelEntity {

    /**
     * 序号
     */
    private Long id;

    /**
     * 登记id
     */
    private Integer registerId;

    /**
     * 创建时间
     */
    private String created;

    /**
     * 修改时间
     */
    private String updated;

    /**
     * 推送状态
     */
    private String pushState;

    /**
     * 推送报文
     */
    private String pushMessage;

    /**
     * 描述
     */
    private String remark;

    /**
     * 推送次数
     */
    private Integer number;

}

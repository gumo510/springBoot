package com.gumo.demo.model.req;

import lombok.Data;

import java.util.List;

/**
 * @Author hy
 * @Date 2022/4/11 11:33
 * @Version 1.0
 */
@Data
public class StatisticsReq {

    /**
     * 查询开始日期
     */
    private String startDate;

    /**
     * 查询结束日期
     */
    private String endDate;

    /**
     * teamOrgIds
     */
    private List<String> teamOrgIds;

    private String tableName;

    private Boolean cancelCache = false;

}

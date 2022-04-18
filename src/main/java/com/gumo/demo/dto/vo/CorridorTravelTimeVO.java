package com.gumo.demo.dto.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author hy
 * @Date 2022/4/11 11:33
 * @Version 1.0
 */
@Data
public class CorridorTravelTimeVO {
    /**
     *  时间区间
     */
    private String travelTime;

    /**
     * 时间区间人数占比
     */
    private BigDecimal odNumber;

    /**
     * 时间区间人数占比
     */
    private String odRate;
}

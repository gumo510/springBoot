package com.gumo.demo.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gumo
 */
@Data
@Accessors(chain = true)
public class Weather {

    /**
     * 时间
     */
    public String date;
    /**
     * 周
     */
    public String week;
    /**
     * 地区
     */
    public String area;
    /**
     * 天气
     */
    public String weather;
    /**
     * 最低温度
     */
    public String lowest;
    /**
     * 最高温度
     */
    public String highest;
    /**
     * 建议
     */
    public String tips;

}

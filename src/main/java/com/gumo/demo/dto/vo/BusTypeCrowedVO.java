package com.gumo.demo.dto.vo;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author hy
 * @since 2022-02-21 14:12
 */
@Data
public class BusTypeCrowedVO {

    private String typeName; //车型

    private Integer maxLooseLimit = 0; //最大宽松阈值

    private Integer maxNormalCrowedLimit = 0; //最大一般拥挤阈值

    private Integer maxMidCrowedLimit = 0;//最大较拥挤阈值

    private Integer hardCrowedLimit = 0;//拥挤阈值

}

package com.gumo.demo.dto.vo;

import com.gumo.demo.entity.CameraInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author gumo
 */
@Data
public class CameraOperateDTO extends CameraInfo {

    /**
     * 操作类型 0-新增 1-更新 2-删除
     */
    private Integer operateType;

    private Date operateDate;
    
}

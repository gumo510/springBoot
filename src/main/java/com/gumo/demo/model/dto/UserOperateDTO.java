package com.gumo.demo.model.dto;

import com.gumo.demo.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * @author gumo
 */
@Data
public class UserOperateDTO extends User {

    /**
     * 操作类型 0-新增 1-更新 2-删除
     */
    private Integer operateType;

    private Date operateDate;
}

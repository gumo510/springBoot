package com.gumo.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 类型
 * </p>
 *
 * @author gumo
 * @since 2022-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_base_type")
public class BaseType extends Model<BaseType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private String typeName;//类型id

    private Integer seatNum; //车辆额定座位数

    private Integer loadCapacity; //车辆额定载客人数

    private String looseLimit; //宽松阈值

    private String normalCrowedLimit; // 一般拥挤阈值

    private String midCrowedLimit;  // 较拥挤

    private String hardCrowedLimit; // 拥挤

    private String teamOrgId;

    private Integer doorCount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

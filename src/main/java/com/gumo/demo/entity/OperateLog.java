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
 * 操作日志表
 * </p>
 *
 * @author gumo
 * @since 2024-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_operate_log")
public class OperateLog extends Model<OperateLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作者用户名
     */
    private String userAccount;

    /**
     * 操作者姓名
     */
    private String userName;

    /**
     * 操作者账号权限
     */
    private String userRole;

    /**
     * 操作者所属单位
     */
    private String userOrganization;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当前用户的ip地址
     */
    private String operateIp;

    /**
     * 操作端，WEB,APP
     */
    private String operatePlatform;

    /**
     * 操作类型
     */
    private Integer operateType;

    /**
     * 操作内容
     */
    private String operateContent;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

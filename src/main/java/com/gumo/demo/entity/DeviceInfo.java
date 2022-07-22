package com.gumo.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author intellif
 * @since 2021-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_device_info")
public class DeviceInfo extends Model<DeviceInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID  和基础服务的id一样
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 型号类型（摄像头-0，测温面板—1）
     */
    private Integer modelId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 创建人账号
     */
    private String creater;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 是否删除（0-正常， 1-已删除）
     */
    private Integer deleteSign;

    /**
     * 设备坐标
     */
    private String devicePoint;

    /**
     * 基础服务Id（第三方Id）
     */
    private Long thirdId;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 设备地址
     */
    private String address;

    /**
     * 场所标签
     */
    private String placeLabel;

    private String macAddress;

    private Integer deviceManageId;

    private String rtsp;

    private String geoString;

    /**
     * 端口
     */
    private Integer port;

    private String longitude;

    private String latitude;

    private String loginName;

    private String loginPassword;

    /**
     * 设备在离线状态：1：在线，0：离线  -1：未激活
     */
    private Integer status;

    /**
     * 是否利旧: 利旧-0 新增-1
     */
    private Integer isOld;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

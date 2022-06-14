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
 * 设备扩展字段
 * </p>
 *
 * @author intellif
 * @since 2022-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_camera_info")
public class CameraInfo extends Model<CameraInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 存放设备IP
     */
    private String ip;

    /**
     * 存放设备端口
     */
    private Integer port;

    /**
     * 登录设备的用户名
     */
    private String loginUser;

    /**
     * 登录设备的密码
     */
    private String password;

    /**
     * 摄像所属区域ID
     */
    private Long areaId;

    /**
     * 在线状态（0-未知 1-禁用 2-在线 3-断线）
     */
    private Integer online;

    /**
     * 设备Code
     */
    private String cameraCode;

    /**
     * 离线时间
     */
    private Date lastOfflineTime;

    /**
     * 最后事件上报时间
     */
    private Date lastUploadTime;

    /**
     * 背景图url
     */
    private String backUrl;

    /**
     * 扩展字段1
     */
    private String extendField1;

    /**
     * 扩展字段2
     */
    private String extendField2;

    /**
     * 固件版本
     */
    private String firmwareVersion;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

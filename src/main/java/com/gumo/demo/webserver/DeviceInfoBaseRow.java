package com.gumo.demo.webserver;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Date;

/**
 * @author
 */
@Data
@XStreamAlias("row")
public class DeviceInfoBaseRow extends BaseRow {
    @XStreamAlias("id")
    private Integer id;
    @XStreamAlias("create_time")
    private Date createTime;
    @XStreamAlias("device_name")
    private String deviceName;
    @XStreamAlias("delete_sign")
    private Integer deleteSign;
    @XStreamAlias("ip_address")
    private String ipAddress;
    @XStreamAlias("mac_address")
    private String macAddress;
    @XStreamAlias("longitude")
    private String longitude;
    @XStreamAlias("latitude")
    private String latitude;
    @XStreamAlias("login_name")
    private String loginName;
    @XStreamAlias("login_password")
    private String loginPassword;
    @XStreamAlias("port")
    private Integer port;
    @XStreamAlias("status")
    private Integer status;
}

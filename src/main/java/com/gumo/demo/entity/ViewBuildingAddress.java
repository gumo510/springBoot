package com.gumo.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author intellif
 * @since 2021-08-30
 */
@Data
@Accessors(chain = true)
@TableName("t_view_building_address")
public class ViewBuildingAddress extends Model<ViewBuildingAddress> {

    private static final long serialVersionUID = 1L;

    private String buildingId;

    private String province;

    private String city;

    private String district;

    private String street;

    private String community;

    private String road;

    private String roadNum;

    private String village;

    private String building;

    private String address;

    private String code;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateAddressDate;

    private String publish;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createAddressDate;

    private String realEstateLicenceAddr;

    private String buildingAddr;


}

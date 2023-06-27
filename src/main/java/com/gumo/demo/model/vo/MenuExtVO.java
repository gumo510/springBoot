package com.gumo.demo.model.vo;

import lombok.Data;

@Data
public class MenuExtVO {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单拥有的权限菜单类型，详见字典表
     */
    private String permissionType;

    /**
     * 菜单URL
     */
    private String menuUrl;

    private String remark;

    private Integer type;
}

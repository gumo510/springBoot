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
 * 菜单表
 * </p>
 *
 * @author gumo
 * @since 2023-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    private Long pMenuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 对应的英文code
     */
    private String menuCode;

    /**
     * 菜单URL
     */
    private String menuUrl;

    /**
     * module-模块,tab-Tab页面,page-页面，详情见字典表menu_type
     */
    private String menuType;

    /**
     * 菜单拥有的权限菜单类型，详见字典表
     */
    private String permissionType;

    /**
     * 类型，0：表示要在页面菜单树展示，1，不展示
     */
    private Integer type;

    /**
     * 0-未删除，1-删除
     */
    private Integer isDeleted;

    private Integer sort;

    private String remark;

    /**
     * 创建人账号
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人账号
     */
    private String updator;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 集团类型，0-所有类型集团；1-商场集团；2-店铺集团
     */
    private Integer groupType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

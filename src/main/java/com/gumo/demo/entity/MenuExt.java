package com.gumo.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单扩展表
 * </p>
 *
 * @author gumo
 * @since 2023-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu_ext")
public class MenuExt extends Model<MenuExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 类型，0：公有类型url，1：私有类型url
     */
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

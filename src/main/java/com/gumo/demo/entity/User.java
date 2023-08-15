package com.gumo.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userName")
    private String userName;

    @TableField("passWord")
    private String passWord;

    @TableField("realName")
    private String realName;

    @TableField("key_word")
    private String keyWord;

    @TableField(exist = false)
    private Long salary;

    @TableField(exist = false)
    private List<Dictionary> dictionary;

    public User() {

    }

    public User(Integer id, String userName, String passWord, String realName) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
    }

    public User(String userName, String passWord, String realName) {
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

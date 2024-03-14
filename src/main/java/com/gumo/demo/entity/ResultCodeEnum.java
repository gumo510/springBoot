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
 * 枚举类状态码
 * </p>
 *
 * @author intellif
 * @since 2024-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_result_code_enum")
public class ResultCodeEnum extends Model<ResultCodeEnum> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 状态码
     */
    private Integer resultCode;

    /**
     * 状态码定义
     */
    private String name;

    /**
     * 状态码详细描述
     */
    private String message;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

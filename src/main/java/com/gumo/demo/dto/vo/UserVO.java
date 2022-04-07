package com.gumo.demo.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author hy
 * @date 2022/3/10
 */
@Data
public class UserVO {
    @ExcelIgnore
    private Integer id;
    @ExcelProperty(value = "用户名",index = 0)
    private String userName;
    @ExcelProperty(value = "密码",index = 1)
    private String passWord;
    @ExcelProperty(value = "真实姓名",index = 2)
    private String realName;
}

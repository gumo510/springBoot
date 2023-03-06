package com.gumo.demo.model.dto;


import lombok.Data;

/**
 * 单条数据Item封装
 * @author gumo
 */
@Data
public class DataItem {

    private String value;
    private String color;

    public DataItem(String _value, String _color) {
        this.value = _value;
        this.color = _color;
    }
}


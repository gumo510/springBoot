package com.gumo.demo.model.dto;

import lombok.Data;

/**
 * @author gumo
 * @date 2024/4/18 16:59
 */
@Data
public class AreaExportMsgParam {

    private String name;

    private String msgException;

    public AreaExportMsgParam(String name, String msgException) {
        this.name = name;
        this.msgException = msgException;
    }
}

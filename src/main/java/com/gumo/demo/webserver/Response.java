package com.gumo.demo.webserver;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("Response")
public class Response {
    private String flag;
    private String msg;
    private String pch;
}

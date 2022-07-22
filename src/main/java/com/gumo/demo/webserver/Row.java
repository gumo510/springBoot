package com.gumo.demo.webserver;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
public abstract class Row {
    @XStreamAsAttribute
    private String type;
}

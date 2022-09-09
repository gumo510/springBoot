package com.gumo.demo.webserver;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

/**
 * @author gumo
 */
@Data
public abstract class BaseRow {
    @XStreamAsAttribute
    private String type;
}

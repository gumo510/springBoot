package com.gumo.demo.webserver;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@Data
@XStreamAlias("table")
public class Table {
    @XStreamImplicit
    private BaseRow[] row;
}

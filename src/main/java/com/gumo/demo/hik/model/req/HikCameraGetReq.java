package com.gumo.demo.hik.model.req;

import lombok.Data;

@Data
public class HikCameraGetReq {
    /**
     *  {
     *   "pageNo": 1,
     *   "pageSize": 1000
     *  }
     */

    private Integer pageNo;

    private Integer pageSize;

    public HikCameraGetReq(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}

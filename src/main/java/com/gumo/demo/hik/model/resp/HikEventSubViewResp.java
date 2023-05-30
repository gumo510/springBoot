package com.gumo.demo.hik.model.resp;

import lombok.Data;

import java.util.List;

@Data
public class HikEventSubViewResp {

    List<Detail> detail;

    @Data
    public static class Detail {

        private List<Integer> eventTypes;

        private String eventDest;
    }
}

package com.gumo.demo.hik.model.req;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HikEventSubReq {
    /**
     * {
     *     "eventTypes": [
     *         131329
     *     ],
     *     "eventDest": "https://ip:port/eventRcv",
     *     "subType": 1,
     *     "eventLvl": [
     *         2
     *     ]
     * }
     */

    private List<Integer> eventTypes;

    private String eventDest;

    private Integer subType;

    private List<Integer> eventLvl;

    public HikEventSubReq(String eventTypeStr, String eventDest, Integer subType, String eventLvl) {
        this.eventTypes = Arrays.stream(eventTypeStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());;
        this.eventDest = eventDest;
        this.subType = subType;
        this.eventLvl = Arrays.stream(eventLvl.split(",")).map(Integer::valueOf).collect(Collectors.toList());;
    }
}

package com.gumo.demo.hik.model.req;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HikEventUnSubReq {
    /**
     * {
     *     "eventTypes": [
     *         131329
     *     ]
     * }
     */

    private List<Integer> eventTypes;

    public HikEventUnSubReq(String eventTypeStr) {
        this.eventTypes = Arrays.stream(eventTypeStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());;
    }
}

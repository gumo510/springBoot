package com.gumo.demo.hik.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class HikCameraGetResp {

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalPage;

    private Integer total;

    List<DeviceResource> list;

    @Data
    public static class DeviceResource {

        @JsonProperty("deviceType")
        private String deviceType;
        @JsonProperty("deviceIndexCode")
        private String deviceIndexCode;
        @JsonProperty("collectTime")
        private String collectTime;
        @JsonProperty("regionName")
        private String regionName;
        @JsonProperty("indexCode")
        private String indexCode;
        @JsonProperty("cn")
        private String cn;
        @JsonProperty("treatyType")
        private String treatyType;
        @JsonProperty("manufacturer")
        private String manufacturer;
        @JsonProperty("ip")
        private String ip;
        @JsonProperty("port")
        private Integer port;
        @JsonProperty("online")
        private Integer online;
    }
}

package com.gumo.demo.model.wxwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WxUserDetailReqDto {

    @JsonProperty("user_ticket")
    private String userTicket;
}

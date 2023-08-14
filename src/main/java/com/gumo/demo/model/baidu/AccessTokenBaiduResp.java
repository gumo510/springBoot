package com.gumo.demo.model.baidu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gumo.demo.model.wxwork.WxworkResp;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gumo
 */
@Data
@NoArgsConstructor
public class AccessTokenBaiduResp extends BaiduResp {

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("session_key")
    private String sessionKey;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("session_secret")
    private String sessionSecret;

    public AccessTokenBaiduResp(String errMsg) {
        super(-1L, errMsg);
    }

}

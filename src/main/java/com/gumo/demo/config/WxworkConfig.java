package com.gumo.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WxworkConfig {

    /**
     * 企业微信redis数据库索引
     */
    @Value("${wxwork.redis.dbIndex:2}")
    private Integer redisDbIndex;

    /**
     * 企业ID
     */
    @Value("${wxwork.corpId:ww029cb29d55b64700}")
    private String corpId;

    /**
     * 应用ID
     */
    @Value("${wxwork.agentId:1000002}")
    private Long agentId;

    /**
     * 企业应用的凭证密钥
     */
    @Value("${wxwork.corpSecret:ezi_J0BWeUBbaO9dLWlWLsB53vL1Tul-aVCTrGODazM}")
    private String corpSecret;

    /**
     * 企业微信文本卡片消息跳转链接
     */
    @Value("${wxwork.message.linkUrl:https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww029cb29d55b64700&redirect_uri=https%3A%2F%2Fai-campus-sit.vesionbook.com/user/login&response_type=code&scope=snsapi_privateinfo&state=intellif2023&agentid=1000002#wechat_redirect}")
    private String messageLinkUrl;

    /**
     * 应用ID
     */
    @Value("${wxwork.visitor.agentId:1000004}")
    private Long visitorAgentId;

    /**
     * 企业应用的凭证密钥
     */
    @Value("${wxwork.visitor.corpSecret:ZMgYK6rrIzSghIDaKRXy-tDPMXgMIiM2kwHQhHvxXZw}")
    private String visitorCorpSecret;

    /**
     * 企业微信文本卡片消息跳转链接
     */
    @Value("${wxwork.visitor.message.linkUrl:https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww029cb29d55b64700&redirect_uri=http%3A%2F%2Fai-campus-sit.vesionbook.com%2Fuser%2Flogin%3Fpage%3Dvisitor&response_type=code&scope=snsapi_privateinfo&state=intellif2023&agentid=1000002#wechat_redirect}")
    private String visitorMessageLinkUrl;

    /**
     * 应用ID
     */
    @Value("${wxwork.visitor.agentId.secret.str:1000004,ZMgYK6rrIzSghIDaKRXy-tDPMXgMIiM2kwHQhHvxXZw;1000002,ezi_J0BWeUBbaO9dLWlWLsB53vL1Tul-aVCTrGODazM}")
    private String visitorAgentIdSecretStr;
}

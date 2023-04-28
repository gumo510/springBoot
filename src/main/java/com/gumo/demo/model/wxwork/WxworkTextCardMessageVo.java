package com.gumo.demo.model.wxwork;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Description：企业微信文本卡片消息
 *
 * @author：Gumo
 * @date：2023/3/28 10:11
 * @version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class WxworkTextCardMessageVo {

    /**
     * 消息接收者，多个接收者用‘|’分隔
     */
    private List<String> toUser;

    /**
     * 消息类型
     */
    private String msgType = "textcard";

    /**
     * 应用ID
     */
    private Long agentId;

    /**
     * 企业ID
     */
    private String corpId;

    /**
     * 企业应用的凭证密钥
     */
    private String corpSecret;

    /**
     * 消息标题
     */
    private String title = "会议通知";

    /**
     * 消息内容
     */
    private String description;

    /**
     * 点击消息跳转的URL
     */
    private String url;

    public WxworkTextCardMessageVo(List<String> toUser, String description) {
        this.toUser = toUser;
        this.description = description;
    }

    public WxMessageReqDto toReqDto() {
        WxMessageReqDto reqDto = new WxMessageReqDto();
        reqDto.setToUser(Joiner.on("|").skipNulls().join(Iterables.filter(this.toUser, StringUtils::isNotBlank)));
        reqDto.setMsgType(this.msgType);
        reqDto.setAgentId(this.agentId);
        reqDto.setTextCard(new WxMessageReqDto.Text(this.title, this.description, this.url));
        return reqDto;
    }
}

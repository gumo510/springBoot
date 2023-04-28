package com.gumo.demo.service.wxwork;


import com.gumo.demo.exception.WxworkException;
import com.gumo.demo.model.wxwork.WxworkTextCardMessageVo;

/**
 * Description：企业微信消息接口
 *
 * @author：Gumo
 * @date：2023/3/28 10:13
 * @version: 1.0
 */
public interface WxworkMessageService {

    /**
     * Description：发送文本卡片消息
      * @param vo 消息内容
     * @author Gumo
     * @date 2023/3/28 14:57
     */
    void sendTextCardMessage(WxworkTextCardMessageVo vo) throws WxworkException;


    /**
     * Description：发送文本卡片消息
     * @param vo 消息内容
     * @author Gumo
     * @date 2023/3/28 14:57
     */
    void sendTextCardMessage2(WxworkTextCardMessageVo vo) throws WxworkException;
}

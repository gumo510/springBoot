package com.gumo.demo.feign;


import com.gumo.demo.constants.AlibabaConstant;
import com.gumo.demo.feign.fallback.AlibabaClientFallbackFactory;
import com.gumo.demo.model.alibaba.ChatAlibabaReqDto;
import com.gumo.demo.model.alibaba.ChatAlibabaResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
  * Description：阿里大模型接口 https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.7eaa5fa6TEp8Dz
  * @author Gumo
  * @date 2023/3/28 9:56
  * @version 1.0
  */
@FeignClient(name = AlibabaConstant.ALIBABA_SERVER_NAME,
        url = AlibabaConstant.ALIBABA_URL,
        fallbackFactory = AlibabaClientFallbackFactory.class)
public interface AlibabaClient {

    /**
     * Description：发起一次对话请求
     * @param apiKey 调用接口凭证
     * @param dto 请求参数
     * @return ChatAlibabaResp 返回信息
     * @author Gumo
     * @date 2023/3/28 11:17
     */
    @PostMapping(value = "/api/v1/services/aigc/text-generation/generation", consumes = MediaType.APPLICATION_JSON_VALUE)
    ChatAlibabaResp chatCompletions(@RequestHeader(name = "Authorization") String apiKey, @RequestBody ChatAlibabaReqDto dto);
}

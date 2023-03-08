package com.gumo.demo.controller;


import com.gumo.demo.model.vo.BaseDataRespVo;
import com.gumo.demo.service.IWeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@RestController
@Slf4j
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IWeChatService weChatService;

    @GetMapping("push")
    public BaseDataRespVo push(){
        BaseDataRespVo respVo = weChatService.sendWeChatMessage();
        return respVo;
    }
}

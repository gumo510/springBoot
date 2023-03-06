package com.gumo.demo.controller;


import com.alibaba.fastjson.JSON;
import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.cache.CarTypeCache;
import com.gumo.demo.model.vo.BaseDataRespVo;
import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.model.vo.UserDeviceVO;
import com.gumo.demo.entity.BaseType;
import com.gumo.demo.entity.Dictionary;
import com.gumo.demo.entity.User;
import com.gumo.demo.model.req.UserReq;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.service.IDictionaryService;
import com.gumo.demo.service.IUserService;
import com.gumo.demo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private BusTypeCache busTypeCache;

    @GetMapping("getUser/{id}")
    public User getUser(@PathVariable int id){
        User user = userService.getById(id);
        return user;
    }

    @PostMapping("getUser/export")
    public CommonResult getUser(){
        CommonResult urlStr = userService.getUserExport();
        return urlStr;
    }

    @PostMapping("getUser/export2")
    public CommonResult getUserExport2(){
        CommonResult urlStr = userService.getUserExport2();
        return urlStr;
    }

    @GetMapping("getColorCrowed")
    public Integer getColorCrowed(){
        // 测试Redis缓存
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        // 测试启动缓存
        BaseType carTypeDO = JSON.parseObject(Optional.ofNullable(CarTypeCache.get(Optional.ofNullable("比亚迪K10").orElse(""))).orElse("{}"), BaseType.class);
        System.out.println(JSON.toJSON(carTypeDO));
        return colorCrowedEnum.getValue();
    }


    @PostMapping("save/dictionary")
    public void saveUser(){
        String dir = System.getProperty("user.dir");
        String excelPath = dir+ File.separator+"/doc/区域.xlsx";
        try {
            List<Dictionary> excelEntities = ExcelUtil.readExcel2Bean(new FileInputStream(new File(excelPath)), Dictionary.class);
            dictionaryService.saveBatch(excelEntities);
            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("test/valid")
    public BaseDataRespVo testValid(@Valid @RequestBody UserReq userReq){

        UserDeviceVO userDevice = userService.getUserDevice(userReq.getLogin(), userReq.getPassword());
        return new BaseDataRespVo(userDevice, CommonResultCodeEnum.SUCCESS.getResultCode(), "成功", "SUCCESS");
    }
}

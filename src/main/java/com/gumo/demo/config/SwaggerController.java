package com.gumo.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.entity.UnLoadFileZip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("call/swagger/")
@Api(tags = "文件SwaggerController")
@Slf4j
public class SwaggerController {


    @Autowired
    private UnLoadFileZip unLoadFileZip;

    @PostMapping("uploadMedicalStaffQzjJob")
    @ApiOperation("通过excel上传医护人员数据")
    public JSONObject uploadMedicalStaffQzjJob(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.info("uploadMedicalStaffQzjJob start!");
        try {
//            unLoadFileZip.uploadExcel(uploadFile);
            log.info("uploadMedicalStaffQzjJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadMedicalStaffQzjJob error ", e);
            return null;
        }
    }

    @PostMapping("uploadMedicalStaffImagesJob")
    @ApiOperation("上传医护人员图片数据")
    public JSONObject uploadMedicalStaffImagesJob(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.info("uploadMedicalStaffImagesJob start!");
        try {
//            uploadMedicalStaffQzjJob.uploadImages(uploadFile);
            log.info("uploadMedicalStaffImagesJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadMedicalStaffImagesJob error ", e);
            return null;
        }
    }

    @PostMapping("uploadZipFilesAndParseJob")
    @ApiOperation("上传医护人员图片数据压缩包zip:图片用身份证号命名")
    public JSONObject uploadZipFilesAndParseJob(@RequestParam("uploadZip") MultipartFile uploadZip) {
        log.info("uploadZipFilesAndParseJob start!");
        try {
//            String filesAndParse = unLoadFileZip.uploadZipFilesAndParse(uploadZip);
            log.info("uploadZipFilesAndParseJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadZipFilesAndParseJob error ", e);
            return null;
        }
    }
}

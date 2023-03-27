package com.gumo.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.service.IUploadFileService;
import com.gumo.demo.service.impl.UploadFileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件测试接口
 * @author gumo
 */
@Api(tags = "FileController")
@RestController
@RequestMapping("/file")
@Slf4j
public class UploadFileController {


    @Autowired
    private IUploadFileService uploadFileService;

    @PostMapping("uploadMedicalStaffQzj")
    @ApiOperation("通过excel上传医护人员数据")
    public JSONObject uploadMedicalStaffQzjJob(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.info("uploadMedicalStaffQzjJob start!");
        try {
            uploadFileService.uploadExcel(uploadFile);
            log.info("uploadMedicalStaffQzjJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadMedicalStaffQzjJob error ", e);
            return null;
        }
    }

    @PostMapping("uploadMedicalStaffImages")
    @ApiOperation("上传医护人员图片数据")
    public JSONObject uploadMedicalStaffImagesJob(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.info("uploadMedicalStaffImagesJob start!");
        try {
//            uploadFileService.uploadImages(uploadFile);
            log.info("uploadMedicalStaffImagesJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadMedicalStaffImagesJob error ", e);
            return null;
        }
    }

    @PostMapping("uploadZipFilesAndParse")
    @ApiOperation("上传医护人员图片数据压缩包zip:图片用身份证号命名")
    public JSONObject uploadZipFilesAndParseJob(@RequestParam("uploadZip") MultipartFile uploadZip) {
        log.info("uploadZipFilesAndParseJob start!");
        try {
            String filesAndParse = uploadFileService.uploadZipFilesAndParse(uploadZip);
            log.info("uploadZipFilesAndParseJob end!");
            return JSON.parseObject("SUCCESS");
        } catch (Exception e) {
            log.error("uploadZipFilesAndParseJob error ", e);
            return null;
        }
    }
}

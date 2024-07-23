package com.gumo.demo.controller;


import com.gumo.demo.service.IOperateLogService;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author gumo
 * @since 2024-07-22
 */
@RestController
@RequestMapping("/operateLog")
public class OperateLogController {

    @Autowired
    private IOperateLogService operationLogService;

//    /**
//     * 查询操作日志列表
//     * @param operateLogQuery
//     * @return
//     */
//    @PostMapping(value = "/list/{version}")
//    @ApiOperation(httpMethod = "POST",value = "查询操作日志列表")
//    public BasePageResponse listOperationLogWithPage(@RequestBody OperateLogQuery operateLogQuery) {
//        try {
//            return operationLogService.listOperationLogWithPage(operateLogQuery);
//        }catch (MsgException e) {
//            return new BasePageResponse(null, e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            return new BasePageResponse(null, CodeConst.FAIL, e.getMessage());
//        }
//    }
//
//
//    /**
//     * 导出日志
//     * @param operateLogQuery
//     * @return
//     */
//    @PostMapping(value = "/export/{version}")
//    @ApiOperation(httpMethod = "POST",value = "导出日志")
//    @LogRecordAnnotation(success = "新增设备{{#param.name}}成功",fail = "新增设备{{#param.name}}失败", prefix = "camera",bizNo = OperateTypeConstants.DEVICE)
//    public BaseResponse exportOperationLog(@RequestBody OperateLogQuery operateLogQuery) {
//        try {
//            String uuid = operationLogService.exportOperationLog(operateLogQuery);
//            LogRecordContext.putVariable("cameraNames", tCameraInfos.stream().map(t->t.getName()).collect(Collectors.joining(",")));
//            return new BaseResponse(uuid, CodeConst.OK, BasicinfoSupport.getCauseStr(CodeConst.OK));
//        } catch (Exception e) {
//            return new BaseResponse(null, CodeConst.FAIL, "系统错误，EXCEL导出异常");
//        }
//    }
//
//    /**
//     * 查询导出结果
//     * @param
//     * @return
//     */
//    @PostMapping(value = "/export/result/{version}")
//    @ApiOperation(httpMethod = "POST",value = "查询导出结果")
//    public BaseResponse exportResult(@RequestParam("uuid") String uuid) {
//        try {
//            return operationLogService.exportResult(uuid);
//        } catch (Exception e) {
//            return new BaseResponse(null, CodeConst.FAIL, e.getMessage());
//        }
//    }
//
//    /**
//     * 查询导出结果
//     * @param
//     * @return
//     */
//    @GetMapping(value = "/operate/type/{version}")
//    @ApiOperation(httpMethod = "POST",value = "查询日志操作类型")
//    public BaseResponse logOperateType() {
//        try {
//            return operationLogService.logOperateType();
//        } catch (Exception e) {
//            return new BaseResponse(null, CodeConst.FAIL, e.getMessage());
//        }
//    }
}

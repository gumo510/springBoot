/*
 * 文件名：CommonResultCodeEnum.java
 * 版权：Copyright by 云天励飞 intellif.com
 * 描述：
 * 创建人：Administrator
 * 创建时间：2018年12月3日
 * 修改理由：
 * 修改内容：
 */

package com.gumo.demo.enums;

/**
 * @author gumo
 */

public enum CommonResultCodeEnum {

    /**
     * 返回描述
     */

    SUCCESS(10000000, "success", "成功"),

    FILE_UPLOAD_ERROR(37000001, "fileUploadError", "图片上传失败"),

    UNKOWN_ERROR(25000001, "unkownError", "未知异常"),

    PARAM_ERROR(25000002, "parameterError", "参数错误"),

    PARAM_INVALID(25000003, "parameterInvalid", "请求参数非法"),

    JSON_ERROR(25000004, "jsonError", "JSON转换失败"),

    PERMISSION_ERROR(403, "permissionError", "您无访问权限，请联系管理员!"),

    USER_INFO_EXPIRE(401, "userInfoExpore", "用户信息登录信息过期,请重新登录!"),

    AUTH_INFO_IS_BLANK(18090001, "AUTHINFOISBLANK", "认证信息为空"),

    DB_ERROR(25000005, "dbError", "数据库异常"),

    NETWORK_ERROR(25000006, "networkError", "网络异常"),

    HANDLE_DATA_EXCEPTION(25000007, "handleDataException", "数据处理异常"),

    FUSE_ERROR(25000008, "fuseError", "进入熔断器，调用服务出现异常"),

    BUSNIESS_SET_INTERCEPTOR(25000009, "业务拦截器拦截", "暂无门店或门店未开启相关业务"),

    DEVICE_BATCH_ERROR(25000010, "batchInsertError", "批量导入失败"),

    MQTT_SENT_FAILED(37000000, "mqttSentFail", "MQTT推送失败"),

    PARAM_LACK_ERROR(37000009, "parameterLackError", "参数缺失"),

    NO_ZIPFILE(37000002, "压缩包格式", "上传的文件不是zip格式"),

    NO_IMAGE(37000003, "noPic", "zip中无图片"),

    NO_EXCEL(37000004, "noExcel", "zip中无Excel文件或多于一个文件"),

    SIZE_NOT_MATCH(37000005, "zipPicNoMatchExcel", "zip中图片个数与Excel表格行数不一致"),

    IMAGE_NO_FACE(37000006, "imageNoFace", "图片无人脸"),

    IMAGE_ERROR(37000007, "imageError", "图片处理引擎异常"),

    IMAGE_MUTI_FACE(37000008, "imageMutiFace", "图片人脸数大于1"),

    IMAGE_NOT_EXIST(37000009, "imageNotExist", "布控人员图片不存在"),

    DATA_NOT_EXIST(37000010, "dataNotExist", "数据不存在"),

    FILE_EXTENSION_ERROR(37000011, "fileExtensionError", "文件类型错误"),

    DATA_DES_ERROR(73700012, "DATA_DES_ERROR", "数据加解密失败"),

    REQUEST_LIMIT_TYPE_ERROR(71370016, "Unsupported current limiting type", "不支持的限流类型"),

    REQUEST_QPS_LIMIT(71370017, "Currently, other accounts are undergoing related operations. Please wait for the operation to complete", "当前已有其它账号正在进行相关操作，请等待操作完成"),

    REQUEST_CONCURRENCY_LIMIT(71370018, "Currently, other accounts are undergoing related operations. Please wait for the operation to complete", "当前已有其它账号正在进行相关操作，请等待操作完成"),

    REQUEST_LIMIT_HANDLER_ERROR(71370019, "Unsupported current limiting handler", "不支持的限流处理方式");


    private int resultCode;

    /***
     * 状态码定义
     */
    private String name;

    /***
     * 状态码详细描述
     */
    private String message;

    private CommonResultCodeEnum(int resultCode, String name, String message) {
        this.resultCode = resultCode;
        this.name = name;
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public static CommonResultCodeEnum get(int resultCode) {
        if (resultCode > 0) {
            return null;
        }
        for (CommonResultCodeEnum c : CommonResultCodeEnum.values()) {
            if (c.getResultCode() == resultCode) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return message;
    }

}


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

    SUCCESS(10000000, "success", "成功"),
    SYSTEM_ERROR(12000001, "system error:{0}", "系统异常:{0}"),
    LOGIN_ERROR(11000001, "login error", "登录用户名或者密码错误"),
    VERY_CODE_NIL(11000002, "verycode nil", "验证码为空"),
    VERY_CODE_ERROR(11000003, "verycode error", "验证码错误"),
    ACCOUNT_NOT_EXIST(11000004, "account not exist", "您的账号不存在"),
    ACCOUNT_FORBIDDEN(11000005, "account forbidden", "您的账号未启用"),


    ACCOUNT_LOCKED(11000006, "account locked,locked {0}s", "账号已锁定,锁定{0}秒"),
    MULTIPLE_LOGIN_ERRORS(11000007, "multiple login errors,remaining times {0}", "账号或密码错误，还可重试{0}次"),
    ACCOUNT_NO_PERMISSION(11000008, "account no permission", "账号无权限"),

    ACCOUNT_FIRST_LOGIN(11000009, "account First login ", "您的账号首次登录，请修改您的初始密码"),

    ACCOUNT_USERNAME_NULL(11000010, "account username is null ", "您输入的用户名为空，请您重新输入"),

    ACCOUNT_PASSWORD_NULL(11000011, "account old password is null ", "您输入的旧密码为空，请您重新输入"),

    ACCOUNT_NEWPASSWORD_NULL(11000012, "account new password is null ", "您输入的新密码为空，请您重新输入"),

    ACCOUNT_NEWPASSWORD_LIKE_PASSWORD(11000013, "account password like  ", "您输入的新密码和旧密码一致，请您重新输入"),

    ACCOUNT_NEWPASSWORD_ERROR(11000014, "account new  password error  ", "您输入的新密码非法"),

    ACCOUNT_NEWPASSWORD_FAIL(11000015, "account new  password error  ", "第一次修改密码异常，请稍后再试"),
    ACCOUNT_NEWPASSWORD_NO(11000016, "no first update password  ", "非法请求，不许第二次调用此接口"),
    FORGOT_PASSWORD_FILE_ERROR(11000017, "forgot password file error  ", "找回密码文件错误"),
    FORGOT_PASSWORD_FILE_EXPIRED(11000018, "forgot password file expired", "找回密码文件已过期"),

    // 文件存储
    INIT_FILESYSTEM_FAIL(21000001, "init filesystem fail", "初始化文件系统失败"),
    FILE_NOT_EXIST(21000002, "file not exist", "文件不存在"),
    NOT_EXECUTABLE_FILE(21000022, "The import file cannot be an executable file", "导入文件不能是可执行文件"),
    FILE_EXCEED_SIZE_LIMIT(21000023, "File exceeds size limit ", "文件超过大小限制"),
    DIR_ERORR(21000003, "dir erorr", "目录错误"),
    UPGRADE_FILE_ERROR(21000004, "upgrade file error", "请上传正确的升级包"),

    TOKEN_ERROR(12000006, "token error", "token错误"),
    // 一般业务异常
    COMMON_BIZ_FAILED(71100005, "bizFailed", "业务处理失败"),
    // 一般业务出错
    COMMON_BIZ_ERROR(71100006, "bizError", "业务处理出错"),
    // 一般业务出错
    NO_PERMISSION_EXCEPTION(71100007, "noPermission", "无此接口权限"),

    FILE_UPLOAD_ERROR(71370001, "fileUploadError", "图片上传失败"),

    UNKOWN_ERROR(71250001, "unkownError", "未知异常"),

    PARAM_ERROR(71250002, "parameterError", "参数错误"),

    PARAM_INVALID(71250003, "parameter invalid", "请求参数非法"),

    JSON_ERROR(71250004, "jsonError", "JSON转换失败"),

    DB_ERROR(71250005, "dbError", "数据库异常"),

    NETWORK_ERROR(71250006, "networkError", "网络异常"),

    HANDLE_DATA_EXCEPTION(71250007, "handleDataException", "数据处理异常"),

    FUSE_ERROR(71250008, "fuseError", "进入熔断器，调用服务出现异常"),

    BUSNIESS_SET_INTERCEPTOR(71250009, "业务拦截器拦截", "暂无门店或门店未开启相关业务"),

    EXTERNAL_SERVER_ACCESS_ERROR(71250010, "serverError", "%s调用异常"),

    MQTT_SENT_FAILED(71370000, "mqttSentFail", "MQTT推送失败"),

    PARAM_LACK_ERROR(71370009, "parameterLackError", "参数缺失"),

    NO_ZIPFILE(71370002, "压缩包格式", "上传的文件不是zip格式"),

    NO_IMAGE(71370003, "noPic", "zip中无图片"),

    NO_EXCEL(71370004, "noExcel", "zip中无Excel文件或多于一个文件"),

    SIZE_NOT_MATCH(71370005, "zipPicNoMatchExcel", "zip中图片个数与Excel表格行数不一致"),

    NAME_REPEATED(71370012, "nameRepeated", "名称重复"),

    ILLEGAL_OPERATION(71370013, "illegalOperation", "操作非法"),

    PUSH_PERSON_EMPTY(71370014, "pushPersonEmpty", "推送人员不能为空"),

    PUSH_IMAGE_EXIST(71370015, "pushImageExist", "当天已存在推送任务"),

    REQUEST_LIMIT_TYPE_ERROR(71370016, "Unsupported current limiting type", "不支持的限流类型"),

    REQUEST_QPS_LIMIT(71370017, "Currently, other accounts are undergoing related operations. Please wait for the operation to complete", "当前已有其它账号正在进行相关操作，请等待操作完成"),

    REQUEST_CONCURRENCY_LIMIT(71370018, "Currently, other accounts are undergoing related operations. Please wait for the operation to complete", "当前已有其它账号正在进行相关操作，请等待操作完成"),

    REQUEST_LIMIT_HANDLER_ERROR(71370019, "Unsupported current limiting handler", "不支持的限流处理方式"),

    /**
     * 验证码相关(与user center保持一致)
     */
//    VERY_CODE_NIL(13000001, "verificationCodeExpired", "验证码为空"),

    VERY_CODE_EXPIRED(13000002, "verificationCodeExpired", "验证码过期"),

//    VERY_CODE_ERROR(13000003, "verificationCodeExpired", "验证码错误"),

    VERY_CODE_EXISTS(13000004, "VERY_CODE_EXISTS", "验证码已发送"),


    /**
     * 账号管理
     */
    ACCOUNT_IS_NULL(61000009, "ACCOUNT_IS_NULL", "账号不存在"),

    ACCOUNT_ERROR_EXIST(61000010, "ACCOUNT_ERROR_EXIST", "已存在同名账号"),

    INSERT_ACCOUNT_ERROR(61000011, "INSERT_ACCOUNT_ERROR", "新增客户账号失败"),

    UPDATE_ACCOUNT_ERROR(61000012, "UPDATE_ACCOUNT_ERROR", "修改客户账号失败"),

    DELETE_ACCOUNT_ERROR(61000013, "DELETE_ACCOUNT_ERROR", "删除客户账号失败"),

    ACCOUNT_IS_STATUS(61000015, "ACCOUNT_IS_STATUS", "删除失败，账户处于启用状态，不可删除"),

    ACCOUNT_PHONE_ERROR(61000017, "ACCOUNT_PHONE_ERROR", "电话号码输入错误"),

    PASSWORF_IS_EMPTY(61000018, "PASSWORF_IS_EMPTY", "密码为空"),

    ACCOUNTNAME_IS_EMPTY(61000021, "ACCOUNTNAME_IS_EMPTY", "账号名称为空"),

    ACCOUNT_PASSWORD_ERROR(61000023, "ACCOUNT_PASSWORD_ERROR", "密码错误"),

    UPDATE_ROLE_ERROR(61000024, "UPDATE_ROLE_ERROR", "修改客户角色失败"),

    ROLENAME_IS_EMPTY(61000025, "UPDATE_ROLE_ERROR", "角色名称为空"),

    OPERATION_IS_EMPTY(61000026, "OPERATION_IS_EMPTY", "操作权限为必选项"),

    PWD_OLD_ERROR(39000001, "oldPwdError", "旧密码不正确"),

    PWD_NEW_NIL(39000002, "newPwdEmpty", "新密码不能为空"),

    PWD_NEW_EXIST(39000003, "newPwdExist", "不能使用最近六次旧密码"),

    PWD_ENCRYPT_ERROR(39000004, "pwdEncryptError", "密码加密失败"),

    PERSON_DETAIL_ERROR(39000005, "personDetailError", "人员详情查询异常"),

    PERSON_CREATE_ERROR(39000006, "personCreateError", "新增人员异常"),

    PERSON_UPDATE_ERROR(39000007, "personUpdateError", "修改人员异常"),

    PERSON_DELETE_ERROR(39000008, "personDeleteError", "删除人员异常"),

    PERSON_INSERT_BATCH_ERROR(39000009, "personInsertBatchError", "批量新增人员异常"),

    PERSON_AUTHORIZE_ERROR(39000010, "personAuthorizeError", "人员授权异常"),

    PERSON_DROP_AUTHORIZE_ERROR(39000011, "personDropAuthorizeError", "人员删除授权异常"),

    PERSON_AUTHORIZE_PROGRESS_ERROR(39000012, "personAuthorizeProgressError", "人员授权进度查询异常"),

    PERSON_INSERT_BATCH_PROGRESS_ERROR(39000013, "personInsertBatchProgressError", "人员批量新增进度查询异常"),

    UPLOAD_PIC_ERROR(39000014, "uploadPicError", "截取小图上传图片失败"),

    PIC_NO_EXIST_ERROR(39000015, "pic", "图片不存在或格式不对"),

    NO_PERSON_FACE(39000016, "noPersonFace", "无人员头像图片"),

    NO_FACE_FEATURE(39000017, "noFaceFeature", "无人脸或者多人脸"),

    PERSON_DROP_BATCH_ERROR(39000018, "personDropBatchError", "批量删除人员异常"),

    PERSON_INSERT_BATCH_EXCEL_ERROR(39000019, "personInsertBatchExcelError", "人员批量上传文件错误"),

    ACCOUNT_NOT_BIND(39000021, "accountDoesNotBind", "账号未绑定"),

    ACCOUNT_IS_BIND(39000022, "accountDoesIsBind", "账号已绑定openId,无法重复绑定"),

    ACCOUNT_OPENID_NOT_MATCH(39000023, "openIdNotMatch", "openId不匹配"),

    ACCOUNT_FORMAT_ERROR(39000025,"accountIsFormatError","账号格式错误"),

    PERSON_INSERT_BATCH_OUT_TIME_ERROR(39000026, "personInsertBatchError", "批量新增人员超时"),

    DEVICE_FEATURE_LIMIT(39000027, "deviceFeatureLimit", "单台设备授权已超出限制"),

    PIC_NO_BLANK_ERROR(39000028, "pic", "图片不能为空"),

    PERSON_INSERT_BATCH_STATUS_ERROR(39000029, "personInsertBatchStatusError", "停止批量人员入库失败"),

    PERSON_DROP_GROUP_AUTHORIZE_ERROR(39000030, "personDropGroupAuthorizeError", "组织继承的授权不可删除"),

    PRIVACY_AUTHORITY_PWD_OLD_ERROR(39000031, "privacyAuthorityPwdOldError", "旧密码错误"),

    PRIVACY_AUTHORITY_PWD_OLD_EXIST(39000032, "privacyAuthorityPwdOldExist", "不能使用旧密码"),

    PRIVACY_AUTHORITY_PWD_NEW_ERROR(39000033, "privacyAuthorityPwdNewError", "新密码错误"),

    /**
     * 公共组件 图片上传
     */
    IMAGE_FORMAT_ERROR(71480000, "Support IMAGE format jpg/png", "仅支持jpg/png格式图片"),
    IMAGE_NIL(71480001, "IMAGE NIL", "图片为空"),
    IMAGE_NO_FACE(71480006, "imageNoFace", "图片无人脸"),

    IMAGE_STRUCT_ENGINE_ERROR(71480007, "image struct engine error", "图片处理引擎异常"),

    IMAGE_MUTI_FACE(71480008, "imageMutiFace", "图片人脸数大于1"),

    UPLOAD_FILE_ERROR(71480010, "UPLOAD_FILE_ERROR", "上传文件与模板不符"),

    IMAGE_RESOLUTION_RATIO_ERROR(71480011, "IMAGE_RESOLUTION_RATIO_ERROR", "图片分辨率过低"),

    IMAGE_QUALITY_LOW(71480018, "IMAGE_QUALITY_LOW", "图片人脸质量不佳，无法入库"),

    FACE_EDGE(71480030, "FACE_EDGE", "人脸靠近边缘"),

    FACE_NOISE(71480031, "FACE_NOISE", "人脸有噪点"),

    FACE_POSE_QUALITY_LOW(71480032, "FACE_POSE_QUALITY_LOW", "人脸偏转角度过大"),

    FACE_LIGHT_BACK(71480033, "FACE_LIGHT_BACK", "人脸逆光"),

    FACE_LIGHT_FRONT(71480034, "FACE_LIGHT_FRONT", "人脸过曝光"),

    FACE_LIGHT_DARK(71480035, "FACE_LIGHT_DARK", "人脸过暗"),

    FACE_LIGHT_SIDE(71480036, "FACE_LIGHT_SIDE", "人脸侧光"),

    FACE_WITH_MASK(71480037, "FACE_WITH_MASK", "人脸带有口罩"),
    FILE_SYSTEM_ERROR(71480038, "file system error", "文件服务器异常"),

    FACE_IMAGE_BASE64_ERROR(71480038, "FACE_WITH_MASK", "图片不是正确的base64"),


    FACE_IMAGE_URL_ERROR(71480039, "FACE_WITH_MASK", "图片url无法访问"),

    IMAGE_STRUCTURED_ENGINE_UNINITIALIZED(71480040, "IMAGE_STRUCTURED_ENGINE_UNINITIALIZED", "图片结构化引擎未准备"),
    IMAGE_STRUCTURED_ENGINE_BUSY(71480041, "IMAGE_STRUCTURED_ENGINE_BUSY", "图片结构化引擎忙碌中"),

    IMAGE_REPLACE_PATH_ERROR(71480042, "IMAGE_REPLACE_PATH_ERROR", "图片结构化引擎忙碌中"),

    /**
     * 分组模块专用错误码 分组开头-40
     */
    PERSON_PERSON_NO_EXIST_ERROR(71400001, "personNoExistError", "人员编号已存在"),

    GROUP_EXCEED_LIMIT(71400002, "groupExceedLimit", "分组级别超过限制，最多2级"),

    GROUP_List(71400003, "groupList", "分组列表查询异常"),

    GROUP_DELETE(71400004, "groupDelete", "分组删除异常"),

    GROUP_INSERT_GROUPPERSON(71400005, "groupInsertGroupPerson", "分组下新增人员异常"),

    GROUP_DELETE_GROUPPERSON(71400006, "groupDeleteGroupPerson", "分组下删除人员异常"),

    GROUP_GET_DETAILS(71400007, "groupGetDetails", "分组查询详情异常"),

    GROUP_AUTH_DEVICE(71400008, "groupAuthDevice", "分组授权设备异常"),

    GROUP_DELETE_DEVICE(71400009, "groupDeleteDevice", "分组删除设备异常"),

    GROUP_UPDATE_PERSONNUMBER(71400010, "groupUpdatePersonNumber", "更新增加分组的已授权人数异常"),

    GROUP_UPDATEMINUS_PERSONNUMBER(71400011, "groupUpdateMinusPersonNumber", "更新减少分组的已授权人数"),

    GROUP_DROP_GROUPPERSON_BATCH(71400012, "dropGroupPersonBatch", "根据人员ids删除分组与人员的关系"),

    GROUP_DELETE_VISITOR(71400013, "deleteVisitor", "删除访客异常"),

    GROUP_LOWER(71400014, "lowerGroup", "查询下级分组异常"),

    GROUP_LIST_ALL(71400015, "groupListAll", "查询所有分组信息异常"),

    GROUP_CREATE(71400016, "createGroup", "新增分组信息异常"),

    GROUP_UPDATE(71400017, "updateGroup", "修改分组信息异常"),

    EXPORT_UUID_EXPIRED(71400018, "exportUuidExpired", "uuid已失效"),

    EXPORT_FAILURE(71400019, "exportFailure", "导出失败"),

    COMP_SETTING_NAME_NOTNULL(71400020, "compSettingNameNotnull", "企业名称不能为空"),

    COMP_SETTING_LOGO_NOTNULL(71400021, "compSettingLogoNotnull", "企业logo不能为空"),

    /**
     * 访客模块错误码
     * 50
     */
    VISITOR_SELECT_DEVICE_COUNT_ERROR(71500001, "VISITOR_SELECT_DEVICE_COUNT_ERROR", "设备数量查询错误"),
    VISITOR_DEVICE_MAX_COUNT_ERROR(71500002, "VISITOR_DEVICE_MAX_COUNT_ERROR", "图片数量超出限制"),
    VISITOR_NOT_EXISTS_ERROR(71500003, "VISITOR_NOT_EXISTS_ERROR", "当前访客不存在"),
    VISITOR_NOT_UNIQUE_ID_ERROR(71500004, "VISITOR_NOT_UNIQUE_ID_ERROR", "访客id必填"),
    VISITOR_APPROVAL_STATUS_ERROR(71500005, "VISITOR_APPROVAL_STATUS_ERROR", "审核状态错误"),
    VISITOR_APPROVAL_ERROR(71500006, "VISITOR_APPROVAL_ERROR", "部分访客已审核"),

    VISITOR_RECORD_TYPE_ERROR(71500007, "VISITOR_RECORD_TYPE_ERROR", "访客流程类型错误"),
    VISITOR_DEVICE_QRCODE_EMPTY(71500008, "VISITOR_DEVICE_QRCODE_EMPTY", "二维码授权设备不能为空"),
    VISITOR_PHONE_ERROR(71500009, "VISITOR_PHONE_ERROR", "访客手机号错误"),
    VISITOR_NAME_NIL(71500010, "VISITOR_NAME_NIL", "访客姓名为空"),
    VISITOR_NAME_LENGTH_ERROR(71500011, "visitor name length <= 10(zh)/20(en) ", "访客姓名长度须<=10(中文)/20(英文)"),

    VISITOR_INSERT_EXCEPTION(71510001, "VISITOR_INSERT_EXCEPTION", "添加访客异常"),
    VISITOR_UPDATE_EXCEPTION(71510002, "VISITOR_UPDATE_EXCEPTION", "访客更新异常"),
    VISITOR_DELETE_EXCEPTION(71510003, "VISITOR_DELETE_EXCEPTION", "访客删除异常"),
    VISITOR_LIST_EXCEPTION(71510004, "VISITOR_LIST_EXCEPTION", "访客列表查询异常"),
    VISITOR_STATISTIC_EXCEPTION(71510005, "VISITOR_STATISTIC_EXCEPTION", "访客统计查询异常"),
    VISITOR_DETAIL_EXCEPTION(71510006, "VISITOR_DETAIL_EXCEPTION", "访客详情异常"),
    VISITOR_APPROVAL_EXCEPTION(71510007, "VISITOR_APPROVAL_EXCEPTION", "访客审核异常"),
    VISITOR_APPROVAL_RECORD_EXCEPTION(71510008, "VISITOR_APPROVAL_RECORD_EXCEPTION", "访客审核记录异常"),
    VISITOR_APPROVAL_LIST_EXCEPTION(71510009, "VISITOR_APPROVAL_LIST_EXCEPTION", "访客审核列表异常"),
    VISITOR_APPROVAL_STATISTIC_EXCEPTION(71510010, "VISITOR_APPROVAL_STATISTIC_EXCEPTION", "访客审核统计异常"),
    VISITOR_SELECT_DEVICE_EXCEPTION(71510011, "VISITOR_SELECT_DEVICE_ERROR", "获取设备列表异常"),
    VISITOR_START_OR_END_NOTNULL(71510012, "VISITOR_START_OR_END_NOTNULL", "来访开始时间 或 来访结束时间 不为空"),
    //greater than
    VISITOR_START_GT_NOW(71510013, "VISITOR_START_GT_NOW", "来访结束时间 大于 当前时间"),
    //less than
    VISITOR_START_LT_END(71510014, "VISITOR_START_LT_END", "来访开始时间 小于 来访结束时间"),

    // 人证预约
    VISITOR_APPOINTMENT_NAME_NOT_NULL(71510015, "VISITOR_APPOINTMENT_NAME_NOT_NULL", "人证预约任务名称不能为空"),
    VISITOR_APPOINTMENT_NAME_LENGTH_EXCEED_LIMIT(71510016, "VISITOR_APPOINTMENT_NAME_LENGTH_EXCEED_LIMIT", "人证预约任务名称长度超过20字符"),
    VISITOR_APPOINTMENT_EXIST(71510017, "VISITOR_APPOINTMENT_EXIST", "人证预约任务已存在"),
    VISITOR_APPOINTMENT_NOT_EXIST(71510018, "VISITOR_APPOINTMENT_NOT_EXIST", "人证预约任务不存在"),
    VISITOR_APPOINTMENT_DEVICE_NOT_ONLINE(71510019, "VISITOR_APPOINTMENT_DEVICE_NOT_ONLINE", "当前有人证预约设备不在线"),

    //字符数限制
    VISITOR_NAME_MOTHAN_FIFTY(71510020, "VISITOR_NAME_MOTHAN_FIFTY", "姓名类字符数不能超过50"),
    VISITOR_NUM_MOTHAN_FIFTY(71510021, "VISITOR_NUM_MOTHAN_FIFTY", "编号类字符数不能超过50"),
    INTERVIWEE_NOT_EXIST(71510022, "INTERVIWEE_NOT_EXIST", "受访人不存在"),
    INTERVIWEE_NAME_NOT_UNIQUE(71510023, "INTERVIWEE_NAME_NOT_UNIQUE", "受访人姓名不唯一"),
    INTERVIWEE_NAME_NOT_EMPTY(71510024, "INTERVIWEE_NAME_NOT_EMPTY", "受访人姓名不能为空"),
    VISITOR_BATCH_CREATE_ERROR(71510025, "VISITOR_BATCH_CREATE_ERROR", "批量新增访客错误,请下载错误信息"),
    VISITOR_DEVICE_ERROR(71510026, "visitor device not enabled or not present", "访客设备未启用或者不存在"),
    VISITOR_BATCH_EXPORT_ERROR(71510027, "VISITOR_BATCH_EXPORT_ERROR", "访客批量导出失败"),
    VISITOR_BATCH_EXPORT_EMPTY(71510028, "VISITOR_BATCH_EXPORT_EMPTY", "访客导出信息不存在"),
    /**
     * 通行授权、开放平台申请异常
     */
    OPENAPI_REPLY_REPEATED(71600001, "OPENAPI_REPLY_REPEAT", "重复的开放平台申请"),

    PASS_AUTH_OVERLIMIT(71600002, "PASS_AUTH_OVERLIMIT", "本次添加将导致设备授权人数超过%d人限制，请重新选择设备或授权人员。"),

    PASS_RULE_PARSE_EXCEPTION(71600003, "PASS_RULE_PARSE_EXCEPTION", "解析通行规则出错"),

    PASS_RULE_NOT_EXIST(71600004, "PASS_RULE_NOT_EXIST", "通行规则不存在"),

    PASS_RULE_USING(71600004, "PASS_RULE_USING", "授权规则正在使用中"),

    PASS_RULE_REPEATED(71600005, "PASS_RULE_REPEAT", "通行规则名称重复"),

    COMP_SETTING_LIMIT(71600006, "COMP_SETTING_LIMIT", "企业简介不能超过50个字符"),

    EVENT_MESSAGE_LIMIT(71600007, "EVENT_MESSAGE_LIMIT", "添加失败，最多添加100条事件推送"),

    IMAGE_MESSAGE_LIMIT(71600008, "EVENT_MESSAGE_LIMIT", "添加失败，最多添加10条图片推送"),

    PASS_AUTH_PERSONNUM_ERROR(71600009, "PASS_AUTH_PERSONNUM_ERROR", "批量添加人员编号错误,请查看"),

    PASS_RULE_NAME_NULL(71600010, "PASS_RULE_NAME_NULL", "规则名称不能为空"),

    CREATE_PASS_RULE_ERROR(71600011, "CREATE_PASS_RULE_ERROR", "创建通行规则失败"),

    UPDATE_PASS_RULE_ERROR(71600012, "UPDATE_PASS_RULE_ERROR", "修改通行规则名称失败"),

    LIST_PASS_RULE_ERROR(71600013, "LIST_PASS_RULE_ERROR", "通行规则列表查询失败"),

    DELETE_PASS_RULE_ERROR(71600014, "DELETE_PASS_RULE_ERROR", "删除通行规则失败"),

    LIST_PASS_GROUP_ERROR(71600015, "LIST_PASS_GROUP_ERROR", "通行组人员列表查询失败"),

    PASS_GROUP_ADD_PERSON_ERROR(71600016, "PASS_GROUP_ADD_PERSON_ERROR", "通行组新增人员失败"),

    PASS_GROUP_DELETE_PERSON_ERROR(71600017, "PASS_GROUP_DELETE_PERSON_ERROR", "通行组删除人员失败"),

    PASS_GROUP_VIEW_PERSON_ERROR(71600018, "PASS_GROUP_VIEW_PERSON_ERROR", "通行组查看人员失败"),

    PASS_RULE_QUERY_SHIFT_ERROR(71600019, "PASS_RULE_QUERY_SHIFT_ERROR", "查询通行班次失败"),

    PASS_RULE_CONTENT_NULL(71600020, "PASS_RULE_CONTENT_NULL", "规则内容不能为空"),

    PASS_RULE_SHIFT_TIME_NULL(71600021, "PASS_RULE_SHIFT_TIME_NULL", "起止时间不能为空"),

    PASS_RULE_UPDATE_SHIFT_ERROR(71600022, "PASS_RULE_UPDATE_SHIFT_ERROR", "修改通行班次失败"),

    PASS_AUTHORITY_LIST_ERROR(71600023, "PASS_AUTHORITY_LIST_ERROR", "通行授权列表查询失败"),

    PASS_AUTHORITY_DELETE_ERROR(71600024, "PASS_AUTHORITY_DELETE_ERROR", "通行授权清空授权失败"),

    PASS_AUTHORITY_VIEW_ERROR(71600025, "PASS_AUTHORITY_VIEW_ERROR", "通行授权查看失败"),

    PASS_AUTHORITY_QUERY_TYPES_ERROR(71600026, "PASS_AUTHORITY_QUERY_TYPES_ERROR", "通行授权查询授权类型失败"),


    /**
     * 人员模块
     */
    PERSON_NAME_NOT_NULL(71700001, "PERSON_NAME_NOT_NULL", "人员名称不能为空"),
    PERSON_NUM_NOT_NULL(71700002, "PERSON_NUM_NOT_NULL", "人员编号不能为空"),
    PERSON_PHOTOS_NOT_NULL(71700003, "PERSON_PHOTOS_NOT_NULL", "人员头像不能为空"),
    PERSON_PHOTOS_OVER_LIMIT(71700004, "PERSON_PHOTOS_OVER_LIMIT", "超出图片数量个数限制"),
    PERSON_NUM_HAS_EXITS(71700005, "PERSON_NUM_HAS_EXITS", "此人员编号已经存在，不能重复添加！"),
    PERSON_EXCEL_CONTENT_INVALID(71700006, "PERSON_EXCEL_CONTENT_INVALID", "excel内容无效，详见连接"),
    PERSON_NOT_EXISTS(71700007, "PERSON_NOT_EXISTS", "人员不存在"),
    PERSON_PIC_NOT_EXISTS(71700008, "PERSON_PIC_NOT_EXISTS", "人员图片不存在"),
    PERSON_PROMPT_EXCEEDING_CHARACTER_LIMIT(71700009, "PERSON_PROMPT_EXCEEDING_CHARACTER_LIMIT", "新加人员提示语不超过14"),
    PERSON_NAME_EXCEEDING_CHARACTER_LIMIT(71700010, "PERSON_NAME_EXCEEDING_CHARACTER_LIMIT", "人员名称不超过字符20"),
    PERSON_PHONE_NOT_NULL(71700011, "PERSON_PHONE_NOT_NULL", "手机号不能为空"),
    PERSON_PHONE_FORMAT_ERROR(71700012, "PERSON_PHONE_FORMAT_ERROR", "手机号格式有误"),
    PERSON_ID_CARD_FORMAT_ERROR(71700013, "PERSON_ID_CARD_FORMAT_ERROR", "身份证格式有误"),
    PERSON_EMAIL_EXCEEDING_CHARACTER_LIMIT(71700014, "PERSON_EMAIL_EXCEEDING_CHARACTER_LIMIT", "邮箱字符超过50"),
    PERSON_EMAIL_FORMAT_ERROR(71700015, "PERSON_EMAIL_FORMAT_ERROR", "邮箱格式错误"),
    PERSON_BIRTHDAY_FORMAT_ERROR(71700016, "PERSON_BIRTHDAY_FORMAT_ERROR", "出生日期格式错误, yyyy-MM-dd"),
    PERSON_HIRE_DATE_FORMAT_ERROR(71700017, "PERSON_HIRE_DATE_FORMAT_ERROR", "入职日期格式错误, yyyy-MM-dd"),
    PERSON_REMARK_EXCEEDING_CHARACTER_LIMIT(71700018, "PERSON_REMARK_EXCEEDING_CHARACTER_LIMIT", "备注字符超过50"),
    PERSON_TYPE_ERROR(71700019, "PERSON_TYPE_ERROR", "人员类型错误"),
    PERSON_RELATIVE_PHONE_NOT_EXIST(71700020, "PERSON_RELATIVE_PHONE_NOT_EXIST", "关联手机号不存在"),
    PERSON_PHONE_IN_USE_OR_OCCUPIED(71700021, "PERSON_PHONE_IN_USE_OR_OCCUPIED", "该手机号正在被使用中或已被占用"),
    PERSON_CUSTOM_EXPORT_COLUMNS_NOT_NULL(71700022, "PERSON_CUSTOM_EXPORT_COLUMNS_NOT_NULL", "自定义导出字段不能为空"),
    PERSON_NO_EXCEL(71700023, "PERSON_NO_EXCEL", "非Excel文件"),
    PERSON_IMPORT_NOT_EXPORT(71700024, "PERSON_IMPORT_NOT_EXPORT", "检测到导入数据与之前的导出数据格式不一致"),
    PERSON_IMPORT_BATCH_UPDATE_LIMIT(71700025, "PERSON_IMPORT_BATCH_UPDATE_LIMIT_5000", "导入批量更新不超过5000条数据！"),
    PERSON_NUM_EXCEEDING_CHARACTER_LIMIT(71700026, "PERSON_NUM_EXCEEDING_CHARACTER_LIMIT", "人员编号不超过字符14"),
    PERSON_NUM_EXPORT_LIMIT(71700027, "PERSON_NUM_EXPORT_LIMIT", "选择导出设备时,不能超过1000人"),
    PERSON_GET_QRCODE_URL_FAIL(71700028, "PERSON_GET_QRCODE_URL_FAIL", "获取人员二维码失败"),
    PERSON_EXCEL_UPLOAD_FAIL(71700029, "PERSON_EXCEL_UPLOAD_FAIL", "人员批量导入失败"),
    PERSON_UPLOAD_IMAGE_ERROR(71700030, "image struct engine error", "人员批量导入图片检测不通过"),
    PERSON_MAX_LIMIT_OUT(71700031, "PERSON_MAX_LIMIT_OUT", "人员数量超过最大限制！上限1000人"),
    PERSON_COMPLETABLE_FUTURE_ERROR(71700032, "PERSON_COMPLETABLE_FUTURE_ERROR", "人员异步获取规则授权异常"),
    PERSON_IC_CARD_HAS_EXITS(717000033, "PERSON_IC_CARD_HAS_EXITS", "此IC卡号已经存在，不能重复添加！"),

    /**
     * 人员组模块
     */
    GROUP_NAME_NOT_NULL(71800001, "GROUP_NAME_NOT_NULL", "组织名称不能为空"),
    GROUP_PARENT_ID_NOT_NULL(71800002, "GROUP_PARENT_ID_NOT_NULL", "父级id不能为空"),
    GROUP_ROOT_NODE_CANNOT_CREATE(71800003, "GROUP_ROOT_NODE_CANNOT_CREATE", "不能创建顶级节点"),
    GROUP_PARENT_NODE_NOT_EXITS(71800004, "GROUP_PARENT_NODE_NOT_EXITS", "此组织父级节点不存在"),
    GROUP_LEVEL_OVER_LIMIT(71800005, "GROUP_LEVEL_OVER_LIMIT", "分组级别超过限制"),
    GROUP_NODE_NOT_EXITS(71800006, "GROUP_NODE_NOT_EXITS", "不存在人员组, 请先创建人员组!"),
    GROUP_ROOT_NODE_CAN_NOT_DELETE(71800007, "GROUP_ROOT_NODE_CAN_NOT_DELETE", "顶级节点不能删除"),
    GROUP_NAME_HAS_EXITS(71800008, "GROUP_NAME_HAS_EXITS", "组织名称已存在"),
    GROUP_PARAMETER_NOT_NULL(71800009, "GROUP_PARAMETER_NOT_NULL", "人员组参数不能为空!"),
    GROUP_PARAMETER_FORMAT_INVALID(71800010, "GROUP_PARAMETER_FORMAT_INVALID", "组织参数格式有误"),
    GROUP_NAME_EXCEEDING_CHARACTER_LIMIT(71800011, "GROUP_NAME_EXCEEDING_CHARACTER_LIMIT", "组织名称字符超过10"),
    GROUP_8_PERSON_IMPORT_CHANGE_EXCEED(71800012, "GROUP_8_PERSON_IMPORT_CHANGE_EXCEED", "导入数据不大于20000! "),
    GROUP_8_PERSON_IMPORT_CHANGE_NOT_NULL(71800013, "GROUP_8_PERSON_IMPORT_CHANGE_NOT_NULL", "excel内无有效编号! "),
    GROUP_PARENT_NODE_NOT_EQUALS(71800014, "GROUP_PARENT_NODE_NOT_EQUALS", "不可跨级调整顺序"),

    /**
     * 公共推送消息
     */
    MESSAGE_ID_NOT_NULL(71900001, "PUSH_ID_NOT_NULL", "消息ID不能为空"),
    UNIVERSAL_PUSH_NOT_EXIST(71900002, "UNIVERSAL_PUSH_NOT_EXIST", "推送消息不存在"),
    UNIVERSAL_TIME_FORMAT_ERROR(71900003, "UNIVERSAL_TIME_FORMAT_ERROR", "时间格式错误, 为yyyy-MM-dd"),


    /**
     * 设备消息推送
     */
    DEVICE_MESSAGE_EXISTS(71900011, "DEVICE_MESSAGE_EXISTS", "推送任务已存在"),

    DEVICE_MESSAGE_ERROR(71900012, "DEVICE_MESSAGE_ERROR", "消息发送失败"),


    /**
     * applet
     */
    APPLET_NOT_EXIST(72100001, "APPLET_NOT_EXIST", "applet注册关联不存在"),
    APPLET_OPENID_NOT_NULL(72100002, "APPLET_OPENID_NOT_NULL", "openid不能为空"),
    APPLET_UNION_ID_NOT_NULL(72100003, "APPLET_UNION_ID_NOT_NULL", "unionId不能为空"),
    APPLET_PERSON_PHONE_INFO_EXCEPTION(72100004, "APPLET_PERSON_PHONE_INFO_EXCEPTION", "人员手机信息异常, 请联系管理员"),
    APPLET_VERIFY_VISITOR_NOT_EXISTS(72100005, "APPLET_VERIFY_VISITOR_NOT_EXISTS", "审核访客不存在"),

    /**
     * 审核人员
     */
    VERIFY_PERSON_TIME_FORMAT_ERROR(72200001, "VERIFY_PERSON_TIME_FORMAT_ERROR", "时间格式错误, 为yyyy-MM-dd HH:mm:ss"),
    VERIFY_PERSON_NOT_EXISTS(72200002, "VERIFY_PERSON_NOT_EXISTS", "审核人员不存在"),
    VERIFY_PERSON_HAS_PROCESS(72200003, "VERIFY_PERSON_HAS_PROCESS", "人员已审核"),
    VERIFY_PERSON_VERIFY_STATUS_INVALID(72200004, "VERIFY_PERSON_VERIFY_STATUS_INVALID", "审核状态无效"),
    VERIFY_PERSON_REJECT_REASON_CAN_NOT_EMPTY(72200005, "VERIFY_PERSON_REJECT_REASON_CAN_NOT_EMPTY", "拒绝原因不能为空"),
    VERIFY_PERSON_DELETE_EXCEPTION(72200006, "VERIFY_PERSON_DELETE_EXCEPTION", "人员审核列表批量删除异常"),

    /**
     * 考勤班次
     */
    SCHEDULE_NAME_NOT_NULL(72300001, "SCHEDULE_NAME_NOT_NULL", "班次名称不能为空"),
    SCHEDULE_NAME_HAS_EXISTS(72300002, "SCHEDULE_NAME_HAS_EXISTS", "班次名称已存在"),
    SCHEDULE_TIME_NOT_NULL(72300003, "SCHEDULE_TIME_NOT_NULL", "班次时间不能为空"),
    SCHEDULE_TIME_REST_TIME_INVALID(72300004, "SCHEDULE_TIME_REST_TIME_INVALID", "休息时间仅只有一次考勤时间的时候出现"),
    SCHEDULE_NOT_EXISTS(72300005, "SCHEDULE_NOT_EXISTS", "班次不存在"),

    /**
     * 考勤组
     */
    ATT_NAME_NOT_NULL(72310001, "ATT_NAME_NOT_NULL", "考勤组名称不能为空"),
    ATT_SCHEDULES_FORMAT_INVALID(72310002, "ATT_SCHEDULES_FORMAT_INVALID", "考勤班次格式非法, 请按周传入"),
    ATT_NAME_HAS_EXISTS(72310003, "ATT_NAME_HAS_EXISTS", "考勤组名称已存在"),
    ATT_NOT_EXISTS(72310004, "ATT_NOT_EXISTS", "考勤组不存在"),
    ATT_SCHEDULE_IN_USE(72310005, "ATT_SCHEDULE_IN_USE", "考勤班次使用中"),
    ATT_LEAVE_INVALID(72310006, "ATT_LEAVE_INVALID", "异常离岗配置非法"),

    ATT_NAME_EXISTS(72310007, "ATT_NAME_HAS_EXISTS", "考勤规则名称已存在"),

    ATT_EXPORT(72320001, "ATT_EXPORT_ERROR", "导出报表异常"),


    /**
     * 会议相关
     */
    CONFERENCE_EXIST(72400001, "CONFERENCE_EXIST", "该时间段已有会议,请重新选择"),
    MEETING_NAME_EXIST(72400002, "MEETING_NAME_EXIST", "会议室名称不能重复"),
    MEETING_ROOM_EMPTY(72400003, "MEETING_ROOM_EMPTY", "当前会议室不存在,请重新选择"),
    MEETING_ROOM_OCCUPY(72400004, "MEETING_ROOM_OCCUPY", "当前会议室已被占用,请重新选择"),
    DEVICE_NOTEXIST(72400005, "DEVICE_NOTEXIST", "当前设备不存在,请重新选择"),
    PERSON_AUTH_FAIL(72400006, "PERSON_AUTH_FAIL", "参会人员授权失败"),
    PERSON_NOTEXIST(72400007, "PERSON_NOTEXIST", "当前人员不存在"),
    GET_TOKEN_FAILED(72400008, "GET_TOKEN_FAILED", "获取云之家token失败"),
    BEGIN_TIME_ERROR(72400009, "BEGIN_TIME_ERROR", "开始时间不能晚于当前时间,请重新选择"),
    END_TIME_ERROR(72400010, "END_TIME_ERROR", "结束时间不能早于开始时间,请重新选择"),
    ACCOUNT_EXPIRE(72400011, "ACCOUNT_EXPIRE", "您的账号已过期,请重新登录"),
    PROMOTER_NOT_EXIST(72400012, "PROMOTER_NOT_EXIST", "发起人不存在,请重新选择"),
    CREATOR_NOT_EXIST(72400013, "CREATOR_NOT_EXIST", "创建人不存在,请退出会议预定系统"),
    SEND_YUNZHIJIA_MESSAGE_FAIL(72400014, "SEND_YUNZHIJIA_MESSAGE_FAIL", "发送云之家会议消息失败"),
    CONFERENCE_DATE_EMPTY(72400015, "CONFERENCE_DATE_EMPTY", "会议日期不能为空"),
    CONFERENCE_NAME_GREATER_MAX(72400016, "CONFERENCE_NAME_GREATER_MAX", "会议名称长度不能超过20个字符"),
    GROUP_ID_NOT_EMPTY(72400017, "GROUP_ID_NOT_EMPTY", "访客区域不能为空"),
    SYSTEM_BUSY_LATER_RETRY(72400018, "system_busy_later_retry", "系统繁忙，请稍后重试！"),
    GET_FEISHU_USER_INFO_ERROR(72400019, "get_feishu_user_info_error", "飞书用户信息获取失败"),
    INVALID_THIRD_USER_INFO(72400020, "invalid_third_user_info", "该账号暂无权限，请联系管理员"),
    SMS_TEMPLATE_ERROR(72400021, "sms_template_error", "短信模板错误！"),
    GET_OA_USER_INFO_ERROR(72400022, "get_oa_user_info_error", "OA用户信息获取失败"),
    /**
     * 特征值提取
     */
    DEVICE_UNREACHABLE(72500001, "DEVICE_UNREACHABLE", "暂无可用设备"),

    /**
     * 区域设置
     */
    AREA_NAME_EXIST(72501001, "AREA_NAME_EXIST", "区域名称重复"),

    AREA_ID_ERROR(72501002, "AREA_ID_ERROR", "区域ID错误"),

    AREA_SEARCH_URL_ERROR(72501003, "AREA_SEARCH_URL_ERROR", "图片搜索URL错误"),

    AREA_SEARCH_TIME_ERROR(72501004, "AREA_SEARCH_TIME_ERROR", "图片搜索搜索时间段错误"),

    AREA_SEARCH_SIMILAR_ERROR(72501005, "AREA_SEARCH_SIMILAR_ERROR", "图片搜索相似度错误"),

    PAGE_SIZE_ERROR(72501010, "PAGE_SIZE_ERROR", "分页参数错误"),

    AREA_NAME_LENGTH(72501011, "AREA_NAME_LENGTH", "区域名称过长"),

    /**
     * 企业设置
     */
    GET_LOGIN_URL_FAIL(72600001, "GET_LOGIN_URL_FAIL", "获取内部登录码失败"),

    GET_VISITOR_URL_FAIL(72600002, "GET_VISITOR_URL_FAIL", "获取访客二维码失败"),

    GENERATE_PANEL_QRCODE_FAIL(72600003, "GENERATE_PANEL_QRCODE_FAIL", "配置面板机IP/WIFI失败"),

    GENERATE_PANEL_SYSTEM_UPGRADE_FAIL(72600003, "GENERATE_PANEL_SYSTEM_UPGRADE_FAIL", "系统升级失败"),

    SYSTEM_RECOVERY_FAIL(72600004, "SYSTEM_RECOVERY_FAIL", "系统还原失败"),


    /**
     * 访客h5申请
     */
    LOGIN_FAIL(72700001, "LOGIN_FAIL", "手机号码不存在"),
    MANY_TELEPHONE(72700002, "MANY_TELEPHONE", "手机号码不唯一"),
    DEVICE_NOT_EMPTY(72700003, "DEVICE_NOT_EMPTY", "授权设备不能为空"),
    DEVICE_NOT_EXIST(72700004, "DEVICE_NOT_EXIST", "授权设备不存在"),
    VISITOR_NOT_CAPTCHA_ERROR(72700005, "VISITOR_NOT_CAPTCHA_ERROR", "访客验证码必填"),
    VISITOR_EMPTY(72700006, "VISITOR_EMPTY", "访客不存在"),
    /**
     * 体温
     */
    TEMPRATURE_LESS_MIN(72800001, "TEMPRATURE_LESS_MIN", "温度最小值不能低于35度"),
    TEMPRATURE_GREATER_MAX(72800002, "TEMPRATURE_GREATER_MAX", "温度最大值不能高于42度"),
    /**
     * 体温大数据看板
     */
    TEMPRATURE_STATISTIC_FAIL(72900001, "TEMPRATURE_STATISTIC_FAIL", "获取温度统计数据失败"),
    /**
     * 授权同步
     */
    DEVICE_NOT_ONLINE(73000001, "DEVICE_NOT_ONLINE", "当前设备不在线,无法清空授权"),
    DEVICE_NOT_APPLY(73000011, "DEVICE_NOT_APPLY", "当前设备未启用,无法添加到通行规则"),
    DEVICE_EMPTY(73000002, "DEVICE_EMPTY", "设备为空,无法导出数据"),
    GET_DEVICE_DETAIL_ERROR(73000003, "GET_DEVICE_DETAIL_ERROR", "获取设备详情异常"),
    PERMANENT_AUTH_EXIST(73000004, "PERMANENT_AUTH_EXIST", "已存在永久授权"),
    PERMANENT_AUTH_PERSON_NOT_EMPTY(73000005, "PERMANENT_AUTH_PERSON_NOT_EMPTY", "临时授权人员不能为空"),
    /**
     * 识别记录导出
     */
    NUM_GREATER_MAX(73100001, "NUM_GREATER_MAX", "导出数据已超过十万条,请重新选择"),
    ENDTIME_MUST_GREATER_BEGINTIME(73100002, "ENDTIME_MUST_GREATER_BEGINTIME", "结束时间必须大于开始时间"),
    EXPORT_PERSON_RECORD_ERROR(73100003, "EXPORT_PERSON_RECORD_ERROR", "识别记录导出失败"),
    EXPORT_PERSON_RECORD_EMPTY(73100004, "EXPORT_PERSON_RECORD_EMPTY", "识别记录不存在"),
    BEGINTIME_ENDTIME_NOT_NULL(73100005, "BEGINTIME_ENDTIME_NOT_NULL", "请设置导出的识别时间"),
    COMPRESS_REPLACE_IMAGE_ERROR(73100006, "COMPRESS_REPLACE_IMAGE_ERROR", "识别记录替换图片地址失败"),
    /**
     * 临时授权
     */
    TEMPERORY_AUTH_NOT_EXIST(73200001, "TEMPERORY_AUTH_NOT_EXIST", "临时通行授权记录不存在"),
    /**
     * 班车
     */
    DEVICE_EXIST(73400001, "DEVICE_EXIST", "设备已存在"),
    LICENSE_ERROR(73400002, "LICENSE_ERROR", "车牌格式有误"),
    EXCEL_ERROR(73400003, "EXCEL_ERROR", "excel格式不对,请下载正确的模板"),
    BATCH_CREATE_ERROR(73400004, "BATCH_CREATE_ERROR", "批量新增班车错误"),
    LICENSE_NOT_UNIQUE(73400005, "LICENSE_NOT_UNIQUE", "车牌号必须唯一"),
    TRAVEL_TIME_ERROR(73400006, "TRAVEL_TIME_ERROR", "乘车时间段格式不正确"),
    INSERT_BUS_ERROR(73400007, "INSERT_BUS_ERROR", "新增班车错误"),
    UPDATE_BUS_ERROR(73400008, "INSERT_BUS_ERROR", "更新班车错误"),
    TRAVEL_TIME_DUPLICATED(73400009, "TRAVEL_TIME_DUPLICATED", "乘车时间段不能重复"),
    DEVICES_NOT_EXIST(73400010, "DEVICE_NOT_EXIST", "设备不存在"),

    /**
     * 设备，人员，人员组权限
     */
    PERSON_NOT_AUTH(73500001, "PERSON_NOT_AUTH", "无人员权限"),
    PERSON_GROUP_NOT_AUTH(73500002, "PERSON_GROUP_NOT_AUTH", "无人员组权限"),
    DEVICE_NOT_AUTH(73500003, "DEVICE_NOT_AUTH", "无设备权限"),

    /**
     * 订阅
     */
    SUBSCRIBE_METADATA_ERROR(73600001, "SUBSCRIBE_METADATA_ERROR", "订阅失败元数据错误"),
    /**
     * 加密
     */
    ENCRYPTION_PLAINTEXT_ILLEGAL(73700001, "ENCRYPTION_PLAINTEXT_ILLEGAL", "加密明文文本非法"),
    ENCRYPTION_TIMESTAMP_ILLEGAL(73700002, "ENCRYPTION_TIMESTAMP_ILLEGAL", "加密时间戳参数非法"),
    ENCRYPTION_NONCE_ILLEGAL(73700003, "ENCRYPTION_NONCE_ILLEGAL", "加密随机字符串参数非法"),
    AES_KEY_ILLEGAL(73700004, "AES_KEY_ILLEGAL", "不合法的aes key"),
    SIGNATURE_NOT_MATCH(73700005, "SIGNATURE_NOT_MATCH", "签名不匹配"),
    COMPUTE_SIGNATURE_ERROR(73700006, "COMPUTE_SIGNATURE_ERROR", "签名计算失败"),
    COMPUTE_ENCRYPT_TEXT_ERROR(73700007, "COMPUTE_ENCRYPT_TEXT_ERROR", "计算加密文字错误"),
    COMPUTE_DECRYPT_TEXT_ERROR(73700008, "COMPUTE_DECRYPT_TEXT_ERROR", "计算解密文字错误"),
    COMPUTE_DECRYPT_TEXT_LENGTH_ERROR(7370009, "COMPUTE_DECRYPT_TEXT_LENGTH_ERROR", "计算解密文字长度不匹配"),
    COMPUTE_DECRYPT_TEXT_CORP_ID_ERROR(73700010, "COMPUTE_DECRYPT_TEXT_CORP_ID_ERROR", "计算解密文字appKey不匹配"),
    DATA_DES_ERROR(73700012, "DATA_DES_ERROR", "数据加解密失败"),


    FILE_IS_NOT_EXIST(72000001, "fileNotExist", "文件不存在"),

    FILE_IS_NOT_EXCEL(72000002, "fileNotExcel", "文件不是Excel"),

    DEVICE_BATCH_INSERT_FAIL(72000003, "deviceBatchInsertFail", "设备批量新增失败"),

    DEVICE_List_EXCEPTION(72000004, "deviceListException", "设备查询列表异常"),

    DEVICE_EVENT_LIST_EXCEPTION(82000004, "deviceEventListException", "设备事件查询列表异常"),

    DEVICE_APPLY_STATUS_SAME(72000018, "", "授权状态已经是当前状态"),

    DEVICE_UPDATE_FAIL(72000005, "deviceUpdated", "设备修改异常"),

    DEVICE_DROP_BATCH_EXCEPTION(72000006, "deviceDropBatchException", "设备批量删除异常"),

    DEVICE_INSERT_FAIL(72000007, "deviceInsertFail", "设备新增失败"),

    COUNT_DEVICE_FAIL(72000008, "", "设备类型统计失败"),

    DEVICE_DETAIL_FAIL(72000009, "", "设备详情查询失败"),

    DEVICE_EVENT_DETAIL_FAIL(82000009, "", "设备事件详情查询失败"),

    DEVICE_ENABLE_UPGRADE_LIST_FAIL(72000010, "", "升级列表查询失败"),

    DEVICE_DELETE_FAIL(72000011, "", "设备删除失败"),

    DEVICE_WITNESS_LIST_FAIL(72000012, "", "查询关联设备列表异常"),

    DEVICE_ID_IS_EXIST(72000013, "", "设备id添加重复"),

    DEVICE_NAME_EXIST(72000014, "", "设备名称添加重复"),

    DEVICE_IP_EXIST(51000047, "", "设备ip已存在"),

    DEVICE_BATCH_INSERT_EXCEL_FAIL(72000015, "", "EXCEL校验不通过"),

    DEVICE_ID_IS_NO_EXIST(72000016, "", "设备id无效"),

    DEVICE_OFF_UPDATE_NO(72000017, "", "离线设备不能修改"),

    DEVICE_APPLY_DISABLE_NO(72000117,"","单向操作，启用设备不能禁用"),

    DEVICE_APPLY_LIMIT(72000118,"apply devices over limited","启用设备数量超过限制"),

    DEVICE_GROUP_NAME_EXIST(72001018, "DEVICE_GROUP_NAME_EXIST", "设备组名称重复"),

    DEVICE_ENABLE_UPGRADE_FAIL(72000019, "", "设备升级失败"),

    AREA_NOT_EXIST(72001030, "AREA_NOT_EXIST", "区域不存在"),
    DEVICE_NAME_ERROR(72001032, "", "设备名称错误"),
    DEVICE_NO_EXIST(72001033, "", "设备不存在"),

    DEVICE_GROUP_TYPE_ERROR(72001034, "DEVICE_GROUP_TYPE_ERROR", "危化品设备,监控设备与面板机不能设置同一标签"),
    NVR_INSERT_FAIL(72001035, "NVR_INSERT_FAIL", "nvr新增失败"),
    NVR_QUERY_FAIL(72001036, "NVR_QUERY_FAIL", "nvr查询失败"),
    NVR_UPDATE_FAIL(72001037, "NVR_UPDATE_FAIL", "nvr修改失败"),
    NVR_DELETE_FAIL(72001038, "NVR_DELETE_FAIL", "nvr删除失败"),


    DEVICE_EXPORT_EXCEPTION(72001039, "DEVICE_EXPORT_EXCEPTION", "设备列表导出异常"),

    LON_LAT_LIMITS_ERROR(72001040, "LON_LAT_LIMITS_ERROR", "经纬度数值错误，经度[0~180],维度[0~90]"),
    HK_ISC_PLAYBACK_URLS_EXCEPTION(72003041, "hkIscPlaybackUrlsException", "海康ISC获取视频回放异常"),

    NVR_QUERY_ENUM_FAIL(72003042, "NVR_QUERY_ENUM_FAIL", "nvr查询枚举失败"),
    NVR_NON_EXISTENT(72003043, "NVR_NON_EXISTENT", "nvr不存在"),
    ALGORITHM_SOURCE_ERROR(72003044, "ALGORITHM_SOURCE_ERROR", "算法来源错误"),

    DATA_SOURCE_IS_NULL(72003045, "DATA_SOURCE_IS_NULL", "数据来源参数为空"),
    BELONG_TO_NVR_IS_NULL(72003046, "BELONG_TO_NVR_IS_NULL", "NVR参数为空"),
    CHANNEL_NUMBER_IS_NULL(72003047, "CHANNEL_NUMBER_IS_NULL", "通道号参数为空"),
    DEVICE_CONNECT_NON_EXISTENT(72004001, "DEVICE_CONNECT_NON_EXISTENT", "当前设备连接不存在"),
    SIK_SERIALIZE_ERROR(72004002, "SIK_SERIALIZE_ERROR", "设备sik协议异常"),


    DEVICE_MESSAGE_TIMEOUT(72004003, "device message timeout", "设备消息发送超时"),
    DEVICE_MESSAGE_NOT_SUPPORTED(72004003, "device message not supported", "不支持此类消息发送"),
    DEVICE_MESSAGE_SYNC_FAIL(72004004, "device message sync fail", "设备消息同步失败"),


    //盒子授权状态
    BOX_AUTHORIZE_ONGOING(72005001, "box authorize ongoing", "盒子授权中"),
    BOX_AUTHORIZE_SUCCESS(72005002, "box authorize success", "盒子授权成功"),
    BOX_AUTHORIZE_FAIL(72005003, "box authorize fail", "盒子授权失败"),
    BOX_AUTHORIZE_ACQUISITION_FAIL(72005004, "box authorize acquisition failed", "获取盒子授权失败"),
    BOX_INFO_ACQUISITION_FAIL(72005005, "box inf acquisition failed", "获取盒子信息失败"),

    DEVICE_TYPES_QUERY_ERROR(72005006, "DEVICE_TYPES_QUERY_ERROR", "查询设备类型列表失败"),

    GET_DEVICE_ERROR(72005007, "get device setting fail", "获取设备配置失败"),

    DEVICE_EVENT_TYPES_ERROR(72005008, "DEVICE_EVENT_TYPES_ERROR", "查询设备事件类型列表失败"),

    UPGRADE_URL_IS_NULL(72006001,"upgradeUrlIsNull","升级url为空"),
    UPGRADE_EXTRACT_BIN_FILE_ERROR(72006002,"upgradeExtractBinFileError","zip升级提取bin文件失败"),

    /**
     * 系统设置模块
     *
     */
    NET_PARAM_LIST_ERROR(72100000, "NET_PARAM_LIST_ERROR", "查询小站盒子网络失败"),

    NET_PARAM_SYNC_ERROR(72100001, "NET_PARAM_SYNC_ERROR", "配置小站盒子网络失败"),

    NTP_PARAM_QUERY_ERROR(72100002, "NTP_PARAM_QUERY_ERROR", "获取小站盒子NTP参数失败"),

    NTP_PARAM_SET_ERROR(72100003, "NTP_PARAM_SET_ERROR", "配置小站盒子时间设置失败"),

    SET_LOG_DEBUG_ERROR(72100004, "SET_LOG_DEBUG_ERROR", "开启/关闭DEBUG日志失败"),

    LOG_DEBUG_QUERY_ERROR(72100005, "LOG_DEBUG_QUERY_ERROR", "查询DEBUG日志开启状态异常"),


    /**
     * 设备事件模块
     */
    DISPATCH_TASK_TYPES_ERROR(72200000, "DISPATCH_TASK_TYPES_ERROR", "查询事件任务类型列表失败"),
    DISPATCH_TASK_LIST_ERROR(72200001, "DISPATCH_TASK_LIST_ERROR", "查询任务事件列表失败"),

    /**
     * 设备组模块
     */
    DEVICE_GROUP_LIST_ERROR(72300001, "DEVICE_GROUP_LIST_ERROR", "查询设备组列表失败"),
    DEVICE_GROUP_ADD_ERROR(72300002, "DEVICE_GROUP_ADD_ERROR", "新增设备组失败"),
    DEVICE_GROUP_UPDATE_ERROR(72300003, "DEVICE_GROUP_UPDATE_ERROR", "修改设备组失败"),
    DEVICE_GROUP_DELETE_ERROR(72300004, "DEVICE_GROUP_DELETE_ERROR", "删除设备组失败"),
    ADD_DEVICE_TO_GROUP_ERROR(72300005, "ADD_DEVICE_TO_GROUP_ERROR", "添加设备组下的设备失败"),
    DEVICE_GROUP_ID_EMPTY(72300006, "DEVICE_GROUP_ID_EMPTY", "设备组id不能为空"),
    DEVICE_GROUP_OTHER_EXITS(72300007, "DEVICE_GROUP_OTHER_EXITS", "设备已存在其他设备组"),
    DEVICE_GROUP_NAME_EMPTY(72300008, "DEVICE_GROUP_NAME_EMPTY", "设备组名称不能为空"),
    DEVICE_GROUP_NAME_EXITS(72300009, "DEVICE_GROUP_NAME_EXITS", "设备组名称已存在"),

    /**
     * 日志模块
     */
    AUDIT_LOGS_EXPORT_EMPTY(73800001,"Audit log record is empty","审计日志记录为空"),
    ;


    private final int resultCode;

    /***
     * 状态码定义
     */
    private final String name;

    /***
     * 状态码详细描述
     */
    private final String message;

    CommonResultCodeEnum(int resultCode, String name, String message) {
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
        if (resultCode <= 0) {
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

    public static void main(String[] args) {
        for (CommonResultCodeEnum codeEnum : CommonResultCodeEnum.values()) {
            System.out.println("| " + codeEnum.getResultCode() + " | " + codeEnum.getName() + " | " + codeEnum.getMessage() + " |");
        }
    }
}


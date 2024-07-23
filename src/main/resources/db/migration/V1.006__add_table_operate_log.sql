CREATE TABLE if not exists `t_log_type` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
      `log_type` int NOT NULL COMMENT '日志类型枚举',
      `log_type_name` varchar(255) DEFAULT NULL COMMENT '日志类型名称',
      `create_time` datetime NOT NULL COMMENT '创建时间',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作类型表';

CREATE TABLE if not exists `t_operate_log` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL COMMENT '用户id',
    `user_account` varchar(50) NOT NULL COMMENT '操作者用户名',
    `user_name` varchar(50) DEFAULT NULL COMMENT '操作者姓名',
    `user_role` varchar(100) NOT NULL COMMENT '操作者账号权限',
    `user_organization` varchar(50) NOT NULL COMMENT '操作者所属单位',
    `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `operate_ip` varchar(32) DEFAULT NULL COMMENT '当前用户的ip地址',
    `operate_platform` varchar(10) DEFAULT NULL COMMENT '操作端，WEB,APP',
    `operate_type` int DEFAULT NULL COMMENT '操作类型',
    `operate_content` text COMMENT '操作内容',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

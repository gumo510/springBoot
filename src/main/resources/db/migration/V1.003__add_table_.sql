CREATE TABLE IF NOT EXISTS `t_user_device` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user_id` varchar(50) NOT NULL COMMENT '用户ID',
    `device_id` varchar(50) DEFAULT NULL COMMENT '设备id',
    `device_name` varchar(50) DEFAULT NULL COMMENT '设备名称',
    `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `device_id_index` (`device_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户设备关联表';

REPLACE INTO `moe`.`t_user_device`(`id`, `user_id`, `device_id`, `device_name`, `created`) VALUES (1, '401', '10', '设备1', '2023-02-22 14:30:21');
REPLACE INTO `moe`.`t_user_device`(`id`, `user_id`, `device_id`, `device_name`, `created`) VALUES (2, '401', '11', '设备2', '2023-02-22 14:31:16');

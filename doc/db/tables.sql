CREATE TABLE `t_base_type` (
       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       `is_delete` tinyint(1) unsigned DEFAULT '0',
       `type_name` varchar(20) DEFAULT NULL,
       `seat_num` int(10) DEFAULT NULL,
       `load_capacity` int(32) DEFAULT NULL,
       `loose_limit` varchar(32) DEFAULT NULL,
       `normal_crowed_limit` varchar(32) DEFAULT NULL,
       `mid_crowed_limit` varchar(32) DEFAULT NULL,
       `hard_crowed_limit` varchar(32) DEFAULT NULL,
       `team_org_id` varchar(50) DEFAULT NULL,
       `door_count` tinyint(3) DEFAULT NULL,
       PRIMARY KEY (`id`),
       UNIQUE KEY `t_base_type_IDX` (`type_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类型';

CREATE TABLE `user` (
        `id` int(32) NOT NULL AUTO_INCREMENT,
        `userName` varchar(32) NOT NULL,
        `passWord` varchar(50) NOT NULL,
        `realName` varchar(32) DEFAULT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `moe`.`t_base_type`(`id`, `create_time`, `update_time`, `is_delete`, `type_name`, `seat_num`, `load_capacity`, `loose_limit`, `normal_crowed_limit`, `mid_crowed_limit`, `hard_crowed_limit`, `team_org_id`, `door_count`) VALUES (2, '2021-10-22 14:52:35', '2021-10-22 14:52:35', 0, 'Audo', 235, 6, '<10', '10 - 20', '20 - 30', '>40', '', NULL);
INSERT INTO `moe`.`t_base_type`(`id`, `create_time`, `update_time`, `is_delete`, `type_name`, `seat_num`, `load_capacity`, `loose_limit`, `normal_crowed_limit`, `mid_crowed_limit`, `hard_crowed_limit`, `team_org_id`, `door_count`) VALUES (3, '2021-10-22 15:13:31', '2021-10-22 15:13:31', 0, '宝马', 345, 45, '<10', '10 - 20', '20 - 30', '>40', '', NULL);
INSERT INTO `moe`.`t_base_type`(`id`, `create_time`, `update_time`, `is_delete`, `type_name`, `seat_num`, `load_capacity`, `loose_limit`, `normal_crowed_limit`, `mid_crowed_limit`, `hard_crowed_limit`, `team_org_id`, `door_count`) VALUES (4, '2021-10-27 10:36:40', '2021-10-27 10:36:40', 0, '奥迪', 23, 23, '<10', '2 - 23', '20 - 30', '>30', '', NULL);
INSERT INTO `moe`.`t_base_type`(`id`, `create_time`, `update_time`, `is_delete`, `type_name`, `seat_num`, `load_capacity`, `loose_limit`, `normal_crowed_limit`, `mid_crowed_limit`, `hard_crowed_limit`, `team_org_id`, `door_count`) VALUES (5, '2021-11-15 20:04:08', '2021-11-15 20:04:08', 0, '比亚迪K10', 50, 60, '<30', '30-40', '40-60', '>60', '1c9ad8f47537ae8f45ded5ab56602563', NULL);
INSERT INTO `moe`.`t_base_type`(`id`, `create_time`, `update_time`, `is_delete`, `type_name`, `seat_num`, `load_capacity`, `loose_limit`, `normal_crowed_limit`, `mid_crowed_limit`, `hard_crowed_limit`, `team_org_id`, `door_count`) VALUES (6, '2022-02-21 19:03:47', '2022-02-28 16:05:21', 0, 'B120C01', 0, 119, '', '', '', '', '', NULL);

INSERT INTO `moe`.`user`(`id`, `userName`, `passWord`, `realName`) VALUES (1, 'admin', '123456', '管理员');
INSERT INTO `moe`.`user`(`id`, `userName`, `passWord`, `realName`) VALUES (2, 'superuser', 'introcks', '测试');

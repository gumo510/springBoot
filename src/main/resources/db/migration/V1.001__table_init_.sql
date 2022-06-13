CREATE TABLE IF NOT EXISTS `t_base_type` (
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

CREATE TABLE IF NOT EXISTS `user` (
        `id` int(32) NOT NULL AUTO_INCREMENT,
        `userName` varchar(32) NOT NULL,
        `passWord` varchar(50) NOT NULL,
        `realName` varchar(32) DEFAULT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

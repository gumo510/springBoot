CREATE TABLE IF NOT EXISTS event.t_table_records (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `table_type` int NOT NULL COMMENT '表大类型 bigimage-1 smallimage-2',
  `table_ref_type` int NOT NULL COMMENT '表小类型 face-1 car-2 bike-3 event-4',
  `table_name` varchar(25) NOT NULL COMMENT '表名称',
  `table_code` bigint NOT NULL COMMENT '表编号',
  `short_name` varchar(25) DEFAULT NULL COMMENT '表简称',
  `total_num` bigint NOT NULL COMMENT '表记录条数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=607 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分表策略表'
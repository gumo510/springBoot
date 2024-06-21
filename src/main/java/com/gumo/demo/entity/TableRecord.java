package com.gumo.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Evan
 * @ClassName TableDivideServiceImpl
 * @date 2022/4/14 17:21
 * @Version 1.0.0
 * @Description 分表策略实现类
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_table_records")
public class TableRecord implements Serializable {

	private static final long serialVersionUID = -5476786160052688058L;

	@TableId(type = IdType.AUTO)
	private Long id;

	private Date created;

	private Date updated;

	private Date startTime;
	
	private Date endTime;
	/**
	 * 一级类型
	 * 1-big image  2- small image
	 */
	private Integer tableType;

	/**
	 * 二级类型
	 *  face-1 car-2 bike-3 event-4
	 */
	private Integer tableRefType;
	
	private String tableName;
	
	private long tableCode;
	
	private String shortName;
	
	private long totalNum;

}

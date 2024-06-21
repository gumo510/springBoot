package com.gumo.demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gumo.demo.entity.TableRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Evan
 * @ClassName TableRecordMapper
 * @date 2022/4/15 10:25
 * @Version 1.0.0
 * @Description 分表记录表数据操作层
 **/
@Mapper
@Repository
@DS("db1")
public interface TableRecordMapper extends BaseMapper<TableRecord> {

    /**
     * 根据时间查询此时间后已建好的分表
     *
     * @param time         时间
     * @param tableType    一级类型表
     * @param tableRefType 二级类型表
     * @return
     */
    TableRecord selectCurrentTablesByTime(@Param("time") String time, @Param("tableType") Integer tableType,
                                          @Param("tableRefType") Integer tableRefType);

    /**
     * 查询当前时间已经创建的表记录
     *
     * @param time
     * @param tableType
     * @param tableRefType
     * @return
     */
    List<TableRecord> selectExistTablesByTime(@Param("time") String time, @Param("tableType") Integer tableType,
                                              @Param("tableRefType") Integer tableRefType);

    /**
     * 通过类型查询结束时间表
     *
     * @param tableType
     * @param tableRefType
     * @return
     */
    TableRecord selectLastTimeTableByType(@Param("tableType") Integer tableType,
                                          @Param("tableRefType") Integer tableRefType);

    /**
     * 动态创建事件表
     *
     * @param tableName 表名称
     */
    void createEventTables(@Param("tableName") String tableName);

    /**
     * 动态创建事件大图表
     *
     * @param tableName 表名称
     */
    void createEventImageTables(@Param("tableName") String tableName);

    /**
     * 获取表记录集合
     *
     * @param startTime
     * @param endTime
     * @param tableType
     * @param tableRefType
     * @return
     */
    List<TableRecord> getTableRecordList(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                         @Param("tableType") Integer tableType, @Param("tableRefType") Integer tableRefType);
}

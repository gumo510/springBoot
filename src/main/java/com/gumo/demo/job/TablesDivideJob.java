package com.gumo.demo.job;


import com.gumo.demo.constants.GlobalConstants;
import com.gumo.demo.entity.TableRecord;
import com.gumo.demo.enums.TableRefTypeEnum;
import com.gumo.demo.enums.TableTypeEnum;
import com.gumo.demo.mapper.TableRecordMapper;
import com.gumo.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.List;

/**
 * @author Evan
 * @ClassName TablesDivideJob
 * @date 2022/4/14 15:03
 * @Version 1.0.0
 * @Description 动态表生成策略
 * 分表策略介绍：
 *     分表类型：1：长尾事件分表-t_event;大图分表-t_event_image
 *              2：人脸分表 - t_face 车辆分表 - t_car 非机动车分表 - t_bike
 *                 人车非大图总表 - t_image
 *              ps:长尾事件大图表与人车非分开的原因是为了满足事件和人车非的分表可以按不同策略进行生成
 *                 真实场景下，事件产生的数据远不及人车非的抓拍数据规模，自然就会出现分表策略配置不一致；
 *     分表生成策略：定时频率轮询扫描，默认提前生成配置数量未来表，当扫描已不足配置数量表时进行自动创建；
 **/
@Component
@Slf4j
public class TablesDivideJob implements Runnable{

    //默认提前生成事件表数量
    @Value("${event.table.default.nums:7}")
    private Integer eventTableNums;
    //设置时间表分片时间间隔
    @Value("${event.table.default.days:1}")
    private Integer eventTableDays;


    @Autowired
    private TableRecordMapper tableRecordMapper;

//    @PostConstruct
    @Scheduled(cron = "${table.divide.cron:0 0/30 * * * ?}")
    public void run() {
        log.info("start execute create partion table...");
        //初始化事件分区表
        initPartionTable(eventTableNums,eventTableDays, TableTypeEnum.getType(GlobalConstants.T_BIG_IMAGE),
                TableRefTypeEnum.getRefType(GlobalConstants.T_NAME_EVENT),GlobalConstants.T_NAME_EVENT_IMAGE);
        log.info("end execute create pration table...");
    }


    /**
     * 初始化分区表
     * @param tableNums 初始化表数量
     * @param tableDays 分表间隔时间-单位(天)
     * @param tableType 表一级类型 1-bigimage 2-smallimage
     * @param tableRefType 表二级类型  1-face 2-car 3-bike 4-event
     * @param bigShortName 大表简写名称
     */
    private void initPartionTable(Integer tableNums,Integer tableDays, Integer tableType,
                                  Integer tableRefType,String bigShortName){
        String currentDate = DateUtil.getFormatDate(new Date());
        //判断当前时间是否已经存在表，已存在，拉取已创建表张数，补充七张表
        TableRecord tableRecord=tableRecordMapper.selectCurrentTablesByTime(currentDate,tableType,tableRefType);
        if(tableRecord != null){
            //当前表已存在，获取已创建表的张数，对默认张数进行补充
            List<TableRecord> tableRecords = tableRecordMapper.selectExistTablesByTime(currentDate,tableType,tableRefType);
            long currentMaxTableCode = 0;
            Date cEndTime = null;
            if(!CollectionUtils.isEmpty(tableRecords)){
                tableNums = tableNums - tableRecords.size();
                // 获取最大分表编号对象
                Optional<TableRecord> maxObj = tableRecords.stream().max(
                        Comparator.comparingLong(TableRecord::getTableCode));
                currentMaxTableCode = maxObj.get().getTableCode();
                cEndTime = maxObj.get().getEndTime();
            }
            initTables(tableNums,tableDays,currentMaxTableCode,cEndTime,TableRefTypeEnum.
                    getRefName(tableRefType),bigShortName);
        }else{
            //当前表不存在，补充历史表，提前创建未来表张数
            Date currentStartDate = DateUtil.getCurrentDateZeroByDate();
            log.info("事件初始化时间:{}",currentStartDate);
            TableRecord lastTableRecord=tableRecordMapper.selectLastTimeTableByType(tableType,tableRefType);
            if(lastTableRecord == null){
                //尚未开始建过表，直接从当前凌晨时间点开始建表，默认表编号从1开始
                final long defaultTableCode = 0;
                initTables(tableNums,tableDays,defaultTableCode,currentStartDate,TableRefTypeEnum.
                        getRefName(tableRefType),bigShortName);
            }else{
                //已经建过表，把历史遗漏表进行填充
                Date lastStartDate = lastTableRecord.getEndTime();
                long currentMaxTableCode = lastTableRecord.getTableCode();
                int differDays = DateUtil.getBetweenTime(lastStartDate,
                        DateUtil.getAddDay(currentStartDate,tableDays*tableNums),Calendar.DATE);
                //超出范围，多建一张表
                int nTableNums=(int)Math.ceil(differDays * 1.0 /tableDays);
                initTables(nTableNums,tableDays,currentMaxTableCode,lastStartDate,TableRefTypeEnum.
                        getRefName(tableRefType),bigShortName);
            }
        }

    }

    /**
     * 初始化表记录数据
     * @param shortName
     * @param startTime
     * @param endTime
     * @param tableType
     * @param tableRefType
     * @param code
     * @return
     */
    private TableRecord initTableRecord(String shortName,Date startTime,Date endTime,
                                 Integer tableType,Integer tableRefType,Long code){
        TableRecord tableRecord = new TableRecord();
        tableRecord.setCreated(new Date());
        tableRecord.setStartTime(startTime);
        tableRecord.setEndTime(endTime);
        tableRecord.setTableCode(code);
        tableRecord.setTableName(shortName+"_"+code);
        tableRecord.setShortName(shortName);
        tableRecord.setTableType(tableType);
        tableRecord.setTableRefType(tableRefType);
        return tableRecord;
    }

    /**
     * 根据条件创建表
     * @param tableNums 表数量
     * @param tableDays 间隔时间范围
     * @param currentMaxTableCode 当前最大编号
     * @param cEndTime 最终结束时间
     */
    private void initTables(int tableNums,int tableDays,Long currentMaxTableCode,Date cEndTime,
                            String smallShortName,String bigShortName){
        for(long i =1;i<=tableNums;i++){
            //生成表编号
            long cTableCode = currentMaxTableCode + i;
            String eventTableName = smallShortName+"_"+cTableCode;
            String eventImageTableName = bigShortName+"_"+cTableCode;
            //生成表覆盖时间范围
            Date startTime = cEndTime;
            Date endTime = DateUtil.getAddDay(cEndTime,tableDays);
            //重新赋值下一张表的开始时间
            cEndTime = endTime;
            //todo 后续封装下面代码，根据shortname来动态判断生成event还是face等表；采用策略模式
            //事件小图记录
            log.info("建立事件表记录，开始时间:{} -> 结束时间:{}",startTime,endTime);
            TableRecord nEventTableRecord = initTableRecord(smallShortName,startTime,endTime,
                    TableTypeEnum.getType(GlobalConstants.T_SMALL_IMAGE),
                    TableRefTypeEnum.getRefType(GlobalConstants.T_NAME_EVENT),cTableCode);
            tableRecordMapper.createEventTables(eventTableName);
            tableRecordMapper.createEventImageTables(eventImageTableName);
            tableRecordMapper.insert(nEventTableRecord);
            //事件大图记录
            TableRecord nEventImageTableRecord = initTableRecord(bigShortName,startTime,endTime,
                    TableTypeEnum.getType(GlobalConstants.T_BIG_IMAGE),
                    TableRefTypeEnum.getRefType(GlobalConstants.T_NAME_EVENT),cTableCode);
            tableRecordMapper.insert(nEventImageTableRecord);
        }

    }

}

package com.gumo.demo.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线路统计导出
 *
 * @author gumo
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StatisticsLineExportVO {

    @ExcelProperty("线路名称")
    @ColumnWidth(13)
    private String lineName;

    @ExcelProperty("站点数量")
    @ColumnWidth(13)
    private Integer siteCount;

    @ExcelProperty("实际配车")
    @ColumnWidth(13)
    private Integer busCount;

    @ExcelProperty("客流")
    @ColumnWidth(13)
    private Integer count;

    @ExcelProperty("平均车厢人数")
    @ColumnWidth(13)
    private Double avgCarPerNum;

    @ExcelProperty("满载率")
    @ColumnWidth(13)
    private String loadRate;
}
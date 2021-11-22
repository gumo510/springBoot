package com.gumo.demo.entity;

import com.gumo.demo.utils.ExcelUtil;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author gumo
 * 行政区域Excel数据
 * https://juejin.cn/post/6844903975196557320
 *
 *  id	登记ID	创建时间	修改时间	推送状态	推送报文	描述	推送次数
 */
@Data
public class DrugstoreExcelEntity {

    /**
     * 序号
     */
    private Long id;

    /**
     * 登记id
     */
    private Integer registerID;

    /**
     * 创建时间
     */
    private String created;

    /**
     * 修改时间
     */
    private String updated;

    /**
     * 推送状态
     */
    private String pushState;

    /**
     * 推送报文
     */
    private String pushMessage;

    /**
     * 描述
     */
    private String remark;

    /**
     * 推送次数
     */
    private Integer number;




    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        String excelPath = dir+ File.separator+"/doc/推送省药监.xls";
        try {
            List<DrugstoreExcelEntity> excelEntities = ExcelUtil.readExcel2Bean(new FileInputStream(new File(excelPath)), DrugstoreExcelEntity.class);

            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

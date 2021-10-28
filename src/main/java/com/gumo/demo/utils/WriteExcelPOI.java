package com.gumo.demo.utils;//package com.gumo.server.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.intellif.antiepidemic.config.QrCodeConfig;
//import com.intellif.antiepidemic.dto.RegisterExportToExcelDto;
//import com.intellif.cloud.building.file.service.FileService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 将aaa文件中的所有Study Subject ID开头的换成 “研究主题”，Protocol ID开头的换成“协议”。这个可以自己修改，根据需求不同灵活变化
// *
// * @author sun
// */
//@Service
//@Slf4j
//public class WriteExcelPOI {
//    @Resource
//    FileService fileService;
//
//    @Autowired
//    QrCodeConfig qrCodeConfig;
//
//    public String writeExcelPOI(List<RegisterExportToExcelDto> records) {
//        String excelUrl = "";
//        try {
//            //修改d盘的aaa.xlsx文件
//            String fileName = FileCommonUtil.getUploads() + "model.xlsx";
//            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
//            Map<String, String> fields = new HashMap<String, String>(16);
//            //获取要修改字段的集合
//            fields = getFieldMap();
//            //存储aaa文件里的数据
//            String fillStr = "";
//            String[] fillSplit = null;
//            //获取excel表的第一个sheet
//            XSSFSheet xSheet = xwb.getSheetAt(0);
//            //遍历所有的行
//            for (int i = 2; i <= records.size() + 1; i++) {
//                //这行为空执行下次循环
//                if (xSheet.getRow(i) == null) {
//                    xSheet.createRow(i);
//                }
//                RegisterExportToExcelDto RegisterExportToExcelDto = records.get(i - 2);
//                //遍历当前行的所有列
//                for (int j = 0; j <= 18; j++) {
//                    //为空执行下次循环
//                    if (xSheet.getRow(i).getCell(j) == null) {
//                        xSheet.getRow(i).createCell(j);
//                    }
//                    //获取当前单元格的数据
//                    fillStr = (xSheet.getRow(i)).getCell(j).toString();
//                    //切割，本人的数据是以"_"为分隔符的这个可以根据自己情况改变
//                    fillSplit = fillStr.split("_");
//                    //获取单元格对象，这块不能向上边那两句代码那么写，不能用createXXX，用的话会只把第一列的数据改掉
//                    XSSFCell xCell = xSheet.getRow(i).getCell(j);
//
//                    switch (j) {
//                        case 0:
//                            //修改数据，看数据是否和字段集合中的数据匹配，不匹配使用元数据
//                            xCell.setCellValue(i - 1);
//                            break;
//                        case 1:
//                            if (RegisterExportToExcelDto.getIsPointDrugstore() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getIsPointDrugstore());
//
//                            }
//                            break;
//                        case 2:
//                            if (RegisterExportToExcelDto.getName() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getName());
//                            }
//                            break;
//                        case 3:
//                            if (RegisterExportToExcelDto.getPhone() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPhone());
//                            }
//                            break;
//                        case 4:
//                            if (RegisterExportToExcelDto.getCid() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getCid());
//                            }
//                            break;
//                        case 5:
//                            if (RegisterExportToExcelDto.getPatientName() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientName());
//                            }
//                            break;
//                        case 6:
//                            if (RegisterExportToExcelDto.getPatientPhone() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientPhone());
//                            }
//                            break;
//                        case 7:
//                            if (RegisterExportToExcelDto.getPatientGender() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientGender());
//
//                            }
//                            break;
//                        case 8:
//                            if (RegisterExportToExcelDto.getPatientCid() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientCid());
//
//                            }
//                            break;
//                        case 9:
//                            xCell.setCellValue(RegisterExportToExcelDto.getPatientCommunity());
//                            break;
//                        case 10:
//                            if (RegisterExportToExcelDto.getPatientArea() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientArea());
//                            }
//
//                            break;
//                        case 11:
//                            if (RegisterExportToExcelDto.getPatientStreet() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientStreet());
//                            }
//                            break;
//                        case 12:
//                            if (RegisterExportToExcelDto.getPatientHomeAddress() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getPatientHomeAddress());
//
//                            }
//                            break;
//                        case 13:
//                            if (RegisterExportToExcelDto.getContactCondition() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getContactCondition());
//
//                            }
//                            break;
//                        case 14:
//                            if (RegisterExportToExcelDto.getFeverOrNot() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getFeverOrNot());
//                            }
//                            break;
//                        case 15:
//                            if (RegisterExportToExcelDto.getTemp() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getTemp());
//
//                            }
//                            break;
//                        case 16:
//                            if (RegisterExportToExcelDto.getDrugName() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getDrugName());
//
//                            }
//                            break;
//                        case 17:
//                            if (RegisterExportToExcelDto.getDrugCount() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getDrugCount());
//                            }
//                            break;
//                        case 18:
//                            if (RegisterExportToExcelDto.getRemark() != null) {
//                                xCell.setCellValue(RegisterExportToExcelDto.getRemark());
//
//                            }
//                            break;
//                    }
//                }
//            }
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            try {
//                xwb.write(bos);
//            } finally {
//                bos.close();
//            }
//            byte[] bytes = bos.toByteArray();
//            log.info("## 开始请求文件服务 ##");
//
//            try{
//                JSONObject objJson = fileService.uploadByByte(bytes, "xlsx");
//                excelUrl = qrCodeConfig.getDownFileHttpPrefix() + objJson.getString("fileId");
//            }catch (Exception e) {
//                log.error("上传excel文件失败", e);
//            }
//            log.info("## 结束请求文件服务:{} ##", excelUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return excelUrl;
//    }
//
//    private Map<String, String> getFieldMap() {
//        Map<String, String> fields = new HashMap<String, String>();
//        fields.put("Study Subject ID", "研究主题");
//        fields.put("Protocol ID", "协议");
//        return fields;
//    }
//
//    public static void main(String[] args) {
//        WriteExcelPOI a = new WriteExcelPOI();
//
//        a.writeExcelPOI(null);
//    }
//}
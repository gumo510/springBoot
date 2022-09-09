package com.gumo.demo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    // excel默认宽度；
    private static int width = 256 * 14;
    // 默认字体
    private static String excelfont = "微软雅黑";

    /**
     * 返回byte[]方式
     *
     * @param title     文档标题
     * @param sheetName 导出的SHEET名字 当前sheet数目只为1
     * @param headers   导出的表格的表头
     * @param dsTitles 导出的数据 map.get(key) 对应的 key
     * @param dsFormat 导出数据的样式 1: left; 2: center 3: right
     * @param widths    表格的列宽度 默认为 256*14
     * @param data      数据集 List<Map>
     * @throws IOException
     */
    public static byte[] export(String title, String sheetName, List<String> headers, List<String> dsTitles,
                                int[] dsFormat, int[] widths, List<Map<String, Object>> data) throws IOException {
        // session.setAttribute("state", null);
        log.info("export------>");
        if (widths == null) {
            widths = new int[dsTitles.size()];
            for (int i = 0; i < dsTitles.size(); i++) {
                widths[i] = width;
            }
        }
        if (dsFormat == null) {
            dsFormat = new int[dsTitles.size()];
            for (int i = 0; i < dsTitles.size(); i++) {
                dsFormat[i] = 1;
            }
        }
        // 创建一个工作薄
        @SuppressWarnings("resource")
        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建一个sheet
        XSSFSheet sheet = wb.createSheet(StringUtils.isNotBlank(sheetName) ? sheetName : "excel");
        // 创建表头，如果没有跳过
        int headerrow = 0;
        if (StringUtils.isNotBlank(title)) {
            XSSFRow row1 = sheet.createRow(headerrow);
            XSSFCell cell = row1.createCell(headerrow);
            //设置单元格内容
            cell.setCellValue(title);
            XSSFCellStyle style = wb.createCellStyle();
            XSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 13);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            cell.setCellStyle(style);
            //合并单   元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dsTitles.size() - 1));
            headerrow++;
        }
        if (headers != null) {
            XSSFRow row = sheet.createRow(headerrow);
            // 表头样式
            XSSFCellStyle style = wb.createCellStyle();
            XSSFFont font = wb.createFont();
//            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font.setFontName(excelfont);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            for (int i = 0; i < headers.size(); i++) {
                sheet.setColumnWidth((short) i, (short) widths[i]);
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(style);
            }
            headerrow++;
        }
        // 表格主体 解析list
        if (data != null) {
            List<XSSFCellStyle> styleList = new ArrayList<>();

            for (int i = 0; i < dsTitles.size(); i++) { // 列数
                XSSFCellStyle style = wb.createCellStyle();
                XSSFFont font = wb.createFont();
                font.setFontName(excelfont);
                font.setFontHeightInPoints((short) 10);
                style.setFont(font);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                if (dsFormat[i] == 1) {
                    style.setAlignment(HorizontalAlignment.LEFT);
                } else if (dsFormat[i] == 2) {
                    style.setAlignment(HorizontalAlignment.CENTER);
                } else if (dsFormat[i] == 3) {
                    style.setAlignment(HorizontalAlignment.RIGHT);

                }
                styleList.add(style);
            }
            for (int i = 0; i < data.size(); i++) { // 行数
                XSSFRow row = sheet.createRow(headerrow);
                Map<String, Object> map = data.get(i);
                for (int j = 0; j < dsTitles.size(); j++) { // 列数
                    XSSFCell cell = row.createCell(j);

                    Object o = map.get(dsTitles.get(j));
                    //增加对对象的处理
                    if (o != null && !(o instanceof String) && !(o instanceof Integer) && !(o instanceof byte[])) {
                        JSONObject obj = JSONObject.parseObject(o.toString());
                        if (obj.size() > 1) {
                            o = obj.get("value");
                        }
                    }

                    if (o == null || "".equals(o)) {
                        cell.setCellValue("");
                    } else if (dsFormat[j] == 4) {
                        // int
                        cell.setCellValue((Long.valueOf(o + "")).longValue());
                    } else if (dsFormat[j] == 5 || dsFormat[j] == 6) {
                        // float
                        cell.setCellValue((Double.valueOf(o + "")).doubleValue());
                    } else {
                        cell.setCellValue(o + "");
                    }

                    cell.setCellStyle((XSSFCellStyle) styleList.get(j));
                }
                headerrow++;
            }
        }

//
//        response.setHeader("Content-disposition", fileName);
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" +  new String(fileName.getBytes(),"ISO-8859-1"));
//        response.setHeader("Pragma", "No-cache");
//        response.setCharacterEncoding("UTF-8");
        ByteArrayOutputStream ouputStream = new ByteArrayOutputStream();
//        OutputStream ouputStream =  new ByteArrayOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
        return ouputStream.toByteArray();
    }

    /**
     *  stolen from  http://www.hougeweb.com/articles/2020/01/15/1579078578778.html
     * @param is
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> readExcel2Bean(InputStream is, Class<T> tClass)
            throws IOException, IllegalAccessException, InstantiationException {
        List<List<String>> list = readExcel(is);
        //-----------------------遍历数据到实体集合开始-----------------------------------
        List<T> listBean = new ArrayList<T>();
        Field[] fields = tClass.getDeclaredFields();
        T uBean = null;
        for (int i = 1; i < list.size(); i++) {// i=1是因为第一行不要
            uBean = (T) tClass.newInstance();
            List<String> listStr = list.get(i);
            for (int j = 0; j < listStr.size(); j++) {
                if (j>=fields.length){
                    break;
                }
                Field field = fields[j];
                String datastring = listStr.get(j);
                field.setAccessible(true);
                if (datastring.length()>0&&datastring!=null) {
                    Class<?> type = field.getType();
                    if (type==String.class){
                        field.set(uBean,datastring);
                    }else  if(type==Integer.class||type==int.class){
                        field.set(uBean,Integer.parseInt(datastring));
                    }else  if(type==Double.class||type==double.class){
                        field.set(uBean,Double.parseDouble(datastring));
                    } else  if(type==Float.class||type==float.class){
                        field.set(uBean,Float.parseFloat(datastring));
                    } else  if(type==Long.class||type==long.class){
                        field.set(uBean,Long.parseLong(datastring));
                    }else if (type==Boolean.class||type==boolean.class){
                        field.set(uBean,Boolean.parseBoolean(datastring));
                    }else if (type==Short.class||type==short.class){
                        field.set(uBean,Short.parseShort(datastring));
                    }else if (type==Byte.class||type==byte.class){
                        field.set(uBean,Byte.parseByte(datastring));
                    }else if (type==Character.class ||type==char.class){
                        field.set(uBean,datastring.charAt(0));
                    }
                }
            }
            listBean.add(uBean);
        }
        return listBean;
    }

    private static List<List<String>> readExcel(InputStream is)
            throws IOException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();
        /** 得到Excel的列数 */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    HSSFDataFormatter hssfDataFormatter = new HSSFDataFormatter();
                    cellValue = hssfDataFormatter.formatCellValue(cell);
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }


}
package com.gumo.demo;

import com.gumo.demo.model.dto.AreaExportMsgParam;
import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.utils.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileTest {

    /**
     * 解压zip 获取config 与bin 文件
     * @param zipFile
     * @return
     * @throws IOException
     */
    private Object extractUpgradeZip(File zipFile) throws IOException {
        File tempFile = null;

        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis, Charset.forName("gbk"))) {

            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    byte[] data = new byte[(int) ze.getSize()];
                    zis.read(data, 0, (int) ze.getSize());

                    String[] arrNames = ze.getName().split("/");
                    String name = arrNames[arrNames.length - 1];
                    if (name.equals("config.json")) {
                        String jsonStr = new String(data);
//                     UpgradeZipConfig upgradeZipConfig = JSONObject.parseObject(jsonStr, UpgradeZipConfig.class);
                    } else {
                        tempFile = File.createTempFile("upload", ".bin");
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(data);
                        }
                    }
                }
            }
        }

        if (tempFile != null) {
            tempFile.deleteOnExit();
        }

        return null;
    }


    /**
     * 解压获取bin 文件
     * @param zipFile
     * @return
     * @throws Exception
     */
    private File extractBinFile(File zipFile) throws Exception {
        // ZIP文件中提取".bin"文件
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                String zipEntryName = zipEntry.getName();
                if (zipEntryName.endsWith(".bin")) {
                    File tempFile = File.createTempFile("upload", ".bin");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    return tempFile;
                }
            }
        }

        return null; // 未找到.bin文件
    }

    private CommonResult excelErrorMsgExport(List<AreaExportMsgParam> msgList) {
        try {
            List<String> headers = new ArrayList<>();
            List<String> ds_titles = new ArrayList<>();
            List<Map<String, Object>> data = new ArrayList<>();

            headers.add("设备名字");
            headers.add("异常信息");

            ds_titles.add("name");
            ds_titles.add("msgException");

            for (AreaExportMsgParam exportMsgParam : msgList) {
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("name", exportMsgParam.getName());
                dataMap.put("msgException", exportMsgParam.getMsgException());
                data.add(dataMap);
            }

            byte[] fileData = ExcelUtil.export(null, null, headers, ds_titles, null, null, data);
//            String path = fileStoreFactory.getFileStoreStrategyService().upload(fileData, "xlsx", null);

            return CommonResult.failed("导出异常文件失败");
        } catch (IOException e) {
            return CommonResult.failed("导出异常文件失败");
        }
    }
}

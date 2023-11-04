package com.gumo.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.gumo.demo.entity.ExcelEntity;
import com.gumo.demo.service.IUploadFileService;
import com.gumo.demo.utils.ExcelUtil;
import com.gumo.demo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements IUploadFileService {


    @Override
    public String uploadExcel(MultipartFile file) throws Exception {
        List<ExcelEntity> excelEntities = ExcelUtil.readExcel2Bean(file.getInputStream(), ExcelEntity.class);
        return null;
    }

    /**
     *  解压压缩包zip
     */
    @Override
    public String uploadZipFilesAndParse(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String fileType = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase(Locale.US);
        String uuid = UUID.randomUUID().toString();
        //判断文件是不是zip类型
        if("zip".equals(fileType)){
            String dir = System.getProperty("user.dir");
            String desPath = "D:/test" + File.separator + uuid.replaceAll("-", "");
            //下面这三行的代码就是把上传文件copy到服务器，一定不要遗漏了。
            //遗漏了这个代码，在本地测试环境不会出问题，在服务器上一定会报没有找到文件的错误
            String savePath = "D:/test" + File.separator ;
            File savefile = new File(savePath + filename);
            file.transferTo(savefile);

            FileUtils fileUtil = new FileUtils();
            //解压zip文件
            FileUtils.unZip(file, desPath, savePath);
            List<File> fileList = new ArrayList<>();
            fileList = fileUtil.getSubFiles(desPath,fileList);
            for (File oneFile : fileList){
                if(oneFile.getName().toLowerCase().endsWith(".jpg")) {
                    try {
                        //解析处理图片文件
                        InputStream fileInputStream  = new FileInputStream(oneFile);
//                        MultipartFile multipartFile = new MockMultipartFile(oneFile.getName(), oneFile.getName(),
//                                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream );
                        System.out.println("1111");
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            //最后要删除文件
            FileUtils.clearFiles(desPath);

        }
        return uuid;
    }

    @Override
    public void processUploadedFile(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);

            // 遍历文档的段落
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();

                // 判断段落是否为标题
                if (paragraph.getStyleID() != null && paragraph.getStyleID().startsWith("Heading")) {
                    System.out.println("标题: " + text);
                } else {
                    System.out.println("内容: " + text);
                }
            }

            document.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        String excelPath = dir+ File.separator+"/doc/推送省药监.xls";
        try {
            List<ExcelEntity> excelEntities = ExcelUtil.readExcel2Bean(new FileInputStream(new File(excelPath)), ExcelEntity.class);
            System.out.println(">>>>>oooooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

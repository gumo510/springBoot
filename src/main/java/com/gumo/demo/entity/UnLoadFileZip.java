package com.gumo.demo.entity;

import com.gumo.demo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
//import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Slf4j
public class UnLoadFileZip {

    /**
     * 这个deomo入参的类型是MultipartFile，很多网上的例子是File类型
     * @param file (zip)
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/addPersonsFileOfZip")
    public String addPersonsFileOfZip(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        //原则上这个uploadZipFilesAndParse方法需要写到service和serviceImpl中
        String result = uploadZipFilesAndParse(file);
        return  result;
    }

    /**
     *解压压缩包zip
     */
    public static String uploadZipFilesAndParse(MultipartFile file) throws Exception {
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
            //解压zip文件，我是写在公共类里面，FileUtil类代码评论区见
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
            //最后要删除文件，删除文件的方法见评论区FileUtil类
            FileUtils.clearFiles(desPath);

        }
        return uuid;
    }
}

package com.gumo.demo.entity;

import com.gumo.demo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
        if(fileType.equals("zip")){

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
//                        parseImageFile(oneFile,createdId,uuid);
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


//    public static void main(String[] args) {
//        String dir = System.getProperty("user.dir");
//        String excelPath = dir + File.separator +"/doc/测试.zip";
//        try {
//            String result = uploadZipFilesAndParse(new MultipartFile(excelPath));
//            System.out.println(">>>>>oooooo");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    /**
     * zipName:要解压缩的文件
     * targetDirName:指定解压路径
     */
/*    public List upzipFile(File zipName, String targetDirName) {
        if (!targetDirName.endsWith(File.separator)) {
            targetDirName += File.separator;
        }
        List<String> NameList = new ArrayList<String>();
        try {
            // 根据zip文件创建ZipFile对象，此类的作用是从zip文件读取条目
            ZipFile zipFile = new ZipFile(zipName);
            ZipEntry zn = null;
            String entryName = null;
            String targetFileName = null;
            byte[] buffer = new byte[4096];
            int bytes_read;
            Enumeration entrys = zipFile.entries();			// 获取ZIP文件里所有的文件条目的名字
            while (entrys.hasMoreElements()) {				// 循环遍历所有的文件条目的名字
                zn = (ZipEntry) entrys.nextElement();
                entryName = zn.getName();				// 获得每一条文件的名字
                targetFileName = targetDirName + entryName;
                if (zn.isDirectory()) {
                    new File(targetFileName).mkdirs();		// 如果zn是一个目录，则创建目录
                    continue;
                } else {
                    new File(targetFileName).getParentFile().mkdirs();// 如果zn是文件，则创建父目录
                }
                File targetFile = new File(targetFileName);	// 否则创建文件
                System.out.println("正在创建文件：" + targetFile.getAbsolutePath());
                String returnName = targetFile.getAbsolutePath();
                FileOutputStream os = new FileOutputStream(targetFile);// 打开文件输出流
                InputStream is = zipFile.getInputStream(zn);	// 从ZipFile对象中打开entry的输入流
                NameList.add(returnName);
                while ((bytes_read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes_read);
                }
                os.close();								// 关闭流
                is.close();
            }
            System.out.println("解压缩"+zipName+"成功！");
        } catch (IOException err) {
            System.err.println("解压缩"+zipName+"失败: " + err);
        }
        return NameList;
    }*/

}

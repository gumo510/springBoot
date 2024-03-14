package com.gumo.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.model.dto.UrlMultipartFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类
 *
 * @author gumo
 * @date 2021/09/03
 */
public class FileUtil {

    /**
     * 检查文件大小
     *
     * @param len  len
     * @param size 大小
     * @param unit 单位
     * @return boolean
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }


    public static MultipartFile base64ToFile(String base64Str) {
        try {
            byte[] faceBytes = Base64Utils.decodeFromString(base64Str);
            InputStream inputStream = new ByteArrayInputStream(faceBytes);
            MultipartFile multipartFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MultipartFile filePathToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/jpg", IOUtils.toByteArray(input));
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input));
        return multipartFile;
    }

    public static String fileToBase64(MultipartFile file) throws Exception{
        String imageStr = Base64.getEncoder().encodeToString(file.getBytes());
        return imageStr;
    }

    private Map<String, StringBuilder> getTargetTextList(MultipartFile file, String targetTitle) throws Exception {

        InputStream inputStream = file.getInputStream();
        XWPFDocument document = new XWPFDocument(inputStream);
        Map<String, StringBuilder> groupedText = new LinkedHashMap<>();
        String currentTitle = null;
        XWPFParagraph parentTitle = null;

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String text = paragraph.getText();
            if(StringUtils.isEmpty(targetTitle)){
                // 获取所有标题内容
                if (isTitle(paragraph)) {
                    currentTitle = text;
                    groupedText.put(currentTitle, new StringBuilder());
                } else if (currentTitle != null) {
                    groupedText.get(currentTitle).append(text);
                } else {
                    currentTitle = "No_Title";
                    groupedText.put(currentTitle, new StringBuilder());
                }
            }else {
                // 获取指定父标题标题下所有内容
                if (isTitle(paragraph)) {
                    if (StringUtils.isEmpty(targetTitle) || text.equals(targetTitle)) {
                        currentTitle = text;
                        parentTitle = paragraph;
                        groupedText.put(currentTitle, new StringBuilder());
                    } else if (!isSubtitle(parentTitle, paragraph)) {
                        currentTitle = null;
                    }
                } else if (currentTitle != null) {
                    groupedText.get(currentTitle).append(text);
                }
            }
        }
        document.close();
        inputStream.close();
        return groupedText;
    }

    public static boolean isTitle(XWPFParagraph paragraph) {

        String styleName = getStyleName(paragraph);
        if(styleName != null && styleName.startsWith("heading")){
            return true;
        }
        if (styleName != null && styleName.matches(".*\\d.*")) {
            return true;
        }
        return false;
    }

    public static boolean isSubtitle(XWPFParagraph parentTitle, XWPFParagraph paragraphTitle) {
        if(Objects.isNull(parentTitle) || Objects.isNull(paragraphTitle)){
            return false;
        }
        // 在这里设置适当的条件以检测子标题
        // 示例: 检查样式名称是否包含数字，并且加粗
        String parentName = getStyleName(parentTitle);
        String styleName = getStyleName(paragraphTitle);
        // 提取两个字符串结尾的数字
        int parentNum = extractTrailingNumber(parentName);
        int paragraphNum = extractTrailingNumber(styleName);
        // 比较提取到的数字
        if(parentNum < paragraphNum){
            return true;
        }else{
            return false;
        }
    }

    public static int extractTrailingNumber(String s) {
        // 使用正则表达式提取字符串结尾的数字
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(s);

        // 如果有匹配的结果，将最后一个数字转换为整数
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        // 如果没有匹配的结果，返回-1
        return -1;
    }

    public static String getStyleName(XWPFParagraph paragraph) {
        String styleId = paragraph.getStyleID();
        if (styleId != null) {
            XWPFStyles styles = paragraph.getDocument().getStyles();
            XWPFStyle style = styles.getStyle(styleId);
            if (style != null) {
                return style.getName();
            }
        }
        return null;
    }

    /**
     * 读取文件 使用正则表达式匹配参数: 文本内容如下
     * Branch=release_v0.0.4_fix_wtw
     * commit 893cf11f2dcf627abcbb70f178e25f03bb0eede1
     * Author: xxx <xx@qq.com>
     * Date:   Wed Mar 13 16:19:12 2024 +0800
     *
     *     feat::gitlab ci测试自建平台
     *
     * @param filePath
     * @param systemVersion
     * @return
     * @throws IOException
     */
    public JSONObject readerSystemVersion(String filePath, String systemVersion) throws IOException {
        JSONObject commitVersionVo = new JSONObject();
        commitVersionVo.put("system", filePath);
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Pattern branchPattern = Pattern.compile("^Branch=(.+)$");
            Pattern commitPattern = Pattern.compile("^commit (.+)$");
            Pattern datePattern = Pattern.compile("^Date:\\s+(.+)$");

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher branchMatcher = branchPattern.matcher(line);
                Matcher commitMatcher = commitPattern.matcher(line);
                Matcher dateMatcher = datePattern.matcher(line);

                if (branchMatcher.find()) {
                    commitVersionVo.put("branch", branchMatcher.group(1));
                } else if (commitMatcher.find()) {
                    commitVersionVo.put("commit", commitMatcher.group(1));
                } else if (dateMatcher.find()) {
                    commitVersionVo.put("date", dateMatcher.group(1));
                }
            }
        } catch (Exception e) {
            throw e;
//            log.error("读取系统版本文件失败 filePath: {}, error: {}", filePath, e);
        }
        return commitVersionVo;
    }


    public static String encodeUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            int lastSlashIndex = path.lastIndexOf('/');
            String fileName = path.substring(lastSlashIndex + 1);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            String newPath = path.substring(0, lastSlashIndex + 1) + encodedFileName;
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), newPath).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to encode URL: " + fileUrl, e);
        }
    }

    public static MultipartFile getMultipartFileFromUrl(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        String contentType = conn.getContentType();
        long size = conn.getContentLengthLong();
        MultipartFile multipartFile = new UrlMultipartFile(in, fileName, contentType, size);
        return multipartFile;
    }

    public static boolean isSupportedFileName(String fileName) {
//        List<String> extList = Arrays.asList("doc", "docx", "pdf");
        return fileName.contains("doc") || fileName.contains("docx") || fileName.contains("pdf");
    }

    public static boolean isSupportedContentType(String contentType) {
        return "application/msword".equals(contentType)
                || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)
                || "application/pdf".equals(contentType);
    }

}

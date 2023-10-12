package com.gumo.demo;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MultipartFileFromUrlTest {
    public static void main1(String[] args) throws IOException {
        String fileUrl = "http://pan.intellif.com:9980/seafhttp/files/73733c89-9793-4ff8-831b-2877984b764b/4.3用餐名单模板.docx";
        String archiveName = "4.3用餐名单模板.docx";
        String encodedUrl = encodeUrl(fileUrl);
        MultipartFile multipartFile = getMultipartFileFromUrl(encodedUrl, archiveName);
        System.out.println(multipartFile);
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
        MultipartFile multipartFile = new UrlMultipartFile(in, fileName,contentType, size);

        return null;
    }

    static class UrlMultipartFile implements MultipartFile {
        private final InputStream inputStream;
        private final String fileName;
        private final String contentType;
        private final long size;

        public UrlMultipartFile(InputStream inputStream, String fileName, String contentType, long size) {
            this.inputStream = inputStream;
            this.fileName = fileName;
            this.contentType = contentType;
            this.size = size;
        }

        @Override
        public String getName() {
            return fileName;
        }

        @Override
        public String getOriginalFilename() {
            return fileName; // 使用一个默认文件名，可以自定义
        }

        @Override
        public String getContentType() {
            return contentType; // 当不需要 contentType 时可以设为 null，或者考虑从 HttpURLConnection 获取 contentType
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return size; // 当不需要使用 getSize() 方法时可以设为 -1，或者考虑使用如 URLConnection.getContentLengthLong() 获取大小
        }

        @Override
        public byte[] getBytes() throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return inputStream;
        }

        @Override
        public void transferTo(File destination) throws IOException, IllegalStateException {
            try (FileOutputStream fos = new FileOutputStream(destination)) {
                fos.write(getBytes());
            }
        }
    }


    public static void main(String[] args) {
        boolean fileName = isSupportedFileName("1 党组织关系转入流程 创业广场党委2023.pdf");

    }

    public static boolean isSupportedFileName(String fileName) {
//        List<String> extList = Arrays.asList("doc", "docx", "pdf");
        return fileName.contains("doc") || fileName.contains("docx") || fileName.contains("pdf");
    }

}

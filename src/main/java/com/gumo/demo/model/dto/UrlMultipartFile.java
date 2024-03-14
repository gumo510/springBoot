package com.gumo.demo.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UrlMultipartFile implements MultipartFile {
    private final InputStream inputStream;
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final long size;

    public UrlMultipartFile(InputStream inputStream, String fileName, String contentType, long size) {
        this.inputStream = inputStream;
        this.name = fileName;
        this.originalFilename = fileName;
        this.contentType = contentType;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
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
        return -1; // 当不需要使用 getSize() 方法时可以设为 -1，或者考虑使用如 URLConnection.getContentLengthLong() 获取大小
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

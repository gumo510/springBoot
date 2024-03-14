package com.gumo.demo.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * 遍历目录获取指定文件
 */
public class FileWalker {
    private List<Path> fileList = new ArrayList<>();
    private String targetFileName;

    public FileWalker(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public void find(Path startPath) throws IOException {
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().equals(targetFileName)) {
                    fileList.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public List<Path> getFiles() {
        return fileList;
    }

    public static void main(String[] args) throws IOException {
        FileWalker fileWalker = new FileWalker("example.txt");
        fileWalker.find(Paths.get("C:\\startingDirectory"));

        for (Path file : fileWalker.getFiles()) {
            System.out.println(file);
        }
    }
}

package com.gumo.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gumo
 * @since 2021-10-28
 */
public interface IUploadFileService {

    /**
     * 入参的类型是MultipartFile
     * @param file (excel)
     * @return
     * @throws Exception
     */
    String uploadExcel(MultipartFile file) throws Exception;

    /**
     * 入参的类型是MultipartFile
     * @param file (zip)
     * @return
     * @throws Exception
     */
    String uploadZipFilesAndParse(MultipartFile file) throws Exception;

    void processUploadedFile(MultipartFile file);
}

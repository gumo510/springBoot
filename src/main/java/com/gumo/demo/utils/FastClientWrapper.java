package com.gumo.demo.utils;


import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * FastDFS工具类
 *
 * @author lx362
 */
@Component
@Slf4j
public class FastClientWrapper {

	/**
	 * Tracker节点的相关信息可以从这里获取
	 */
	@Resource
	private DefaultFastFileStorageClient defaultFastFileStorageClient;
	@Resource
	private AppendFileStorageClient appendFileStorageClient;

	/**
	 * 上传文件
	 *
	 * @param file 文件对象
	 * @return 文件全路径 例如：group1/M00/00/00/wKhjZFsbgEGALzeKAFm1CNSDijc772.jpg
	 */
	public String uploadFile(MultipartFile file, Set<MataData> metaDataSet) throws IOException {
		StorePath storePath = defaultFastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), metaDataSet);
		return storePath.getFullPath();
	}

	/**
	 * 上传文件
	 *
	 * @param content       文件byte[]
	 * @param fileExtension 文件后缀名
	 * @return 文件全路径 例如：group1/M00/00/00/wKhjZFsbgEGALzeKAFm1CNSDijc772.jpg
	 */
	public String uploadFile(byte[] content, String fileExtension, Set<MataData> metaDataSet) {
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		StorePath storePath = defaultFastFileStorageClient.uploadFile(bis, content.length, fileExtension, metaDataSet);
		return storePath.getFullPath();
	}

	/**
	 * 上传文件
	 *
	 * @param inputStream 上传文件流
	 * @param fileSize    文件大小
	 * @param fileExtName 文件扩展名
	 * @param metaDataSet 文件元数据(MataData)
	 * @return 文件全路径 例如：group1/M00/00/00/wKhjZFsbgEGALzeKAFm1CNSDijc772.jpg
	 */
	public String uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MataData> metaDataSet) {
		StorePath storePath = defaultFastFileStorageClient.uploadFile(inputStream, fileSize, fileExtName, metaDataSet);
		return "/"+storePath.getFullPath();
	}

	/**
	 * 删除文件
	 *
	 * @param fileUrl 文件访问地址
	 */
	public boolean deleteFile(String fileUrl) {
		if (StringUtils.isEmpty(fileUrl)) {
			return false;
		}
		try {
			StorePath storePath = StorePathDto.praseFromUrl(fileUrl);
			defaultFastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			return true;
		} catch (FdfsServerException e) {
			log.error("调用fastdfs删除文件失败", e);
		}

		return false;
	}


	/**
	 * 上传图片并且生成缩略图
	 * <pre>
	 * 支持的图片格式包括"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
	 * </pre>
	 *
	 * @param content     文件byte[]
	 * @param fileExtName 文件扩展名
	 * @param metaDataSet 文件元数据(MateData)
	 * @return 文件全路径  例如：group1/M00/00/00/wKhjZFsbgEGALzeKAFm1CNSDijc772.jpg
	 */
	public String uploadImageAndCrtThumbImage(byte[] content, String fileExtName, Set<MataData> metaDataSet) {
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		StorePath storePath = defaultFastFileStorageClient.uploadImageAndCrtThumbImage(bis, content.length, fileExtName, metaDataSet);
		return storePath.getFullPath();
	}


	/**
	 * 下载整个文件
	 *
	 * @param groupName 分组名 group = "group1"
	 * @param path      文件名 path = "M00/01/99/wKgLx1s7LEGAL9L_AAA-TOZofoE145.jpg"
	 * @return 文件byte[]
	 */
	public byte[] downloadFile(String groupName, String path) {
		DownloadByteArray callback = new DownloadByteArray();
		return defaultFastFileStorageClient.downloadFile(groupName, path, callback);
	}

	/**
	 * 下载文件片段
	 *
	 * @param groupName  分组名 group = "group1"
	 * @param path       文件名 path = "M00/01/99/wKgLx1s7LEGAL9L_AAA-TOZofoE145.jpg"
	 * @param fileOffset
	 * @param fileSize   文件大小
	 * @return 文件byte[]
	 */
	public byte[] downloadFile(String groupName, String path, long fileOffset, long fileSize) {
		DownloadByteArray callback = new DownloadByteArray();
		return defaultFastFileStorageClient.downloadFile(groupName, path, fileOffset, fileSize, callback);
	}

	/**
	 * 查看文件的信息
	 *
	 * @param groupName 分组名 group = "group1"
	 * @param path      文件名 path = "M00/01/99/wKgLx1s7LEGAL9L_AAA-TOZofoE145.jpg"
	 * @return 文件的基础信息
	 */
	public FileInfo queryFileInfo(String groupName, String path) {
		return defaultFastFileStorageClient.queryFileInfo(groupName, path);
	}

	/**
	 * 获取metadata
	 *
	 * @param groupName 分组名 group = "group1"
	 * @param path      文件名 path = "M00/01/99/wKgLx1s7LEGAL9L_AAA-TOZofoE145.jpg"
	 * @return 文件元数据(MateData)
	 */
	public Set<MataData> getMetadata(String groupName, String path) {
		return defaultFastFileStorageClient.getMetadata(groupName, path);
	}


	/**
	 * 先上传appender类型的文件
	 *
	 * @param file      文件
	 * @param groupName 组名
	 * @return 存储文件的路径信息
	 * @throws IOException
	 */
	public StorePath uploadAppenderFile(MultipartFile file, String groupName) throws IOException {
		StorePath path = appendFileStorageClient.uploadAppenderFile(groupName, file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()));
		log.debug("上传文件 result={}", path);
		return path;
	}


	/**
	 * 修改续传文件的内容
	 *
	 * @param path
	 * @param file
	 * @param currByte
	 * @throws IOException
	 */
	public void modifyFile(StorePath path, MultipartFile file, long currByte) throws IOException {
		appendFileStorageClient.modifyFile(path.getGroup(), path.getPath(), file.getInputStream(), file.getSize(), currByte);
	}


	/**
	 * 然后使用 appendFile 合并
	 *
	 * @param secondFile 续传文件
	 * @param path       上传appender类型的文件方法返回的StorePath
	 * @return 文件全路径
	 * @throws IOException
	 */
	public String appendFile(MultipartFile secondFile, StorePath path) throws IOException {
		appendFileStorageClient.appendFile(path.getGroup(), path.getPath(), secondFile.getInputStream(), secondFile.getSize());
		return path.getFullPath();
	}

	/**
	 * 合并文件内容
	 *
	 * @param firstFile  第一个文件
	 * @param secondFile 第二个文件
	 * @return byte[]
	 */
	public byte[] getContent(MultipartFile firstFile, MultipartFile secondFile) throws IOException {
		int fileSize = (int) firstFile.getSize();
		int secondFileSize = (int) secondFile.getSize();
		byte[] fullContent = new byte[fileSize + secondFileSize];
		System.arraycopy(firstFile.getBytes(), 0, fullContent, 0, fileSize);
		System.arraycopy(secondFile.getBytes(), 0, fullContent, fileSize, secondFileSize);
		return fullContent;
	}

}

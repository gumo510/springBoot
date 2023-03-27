package com.gumo.demo.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * @author Zac
 */
@Data
public class StorePathDto extends StorePath {

    /** 解析路径 */
    private static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPARATOR = "/";
    /**
     * group
     */
    private static final String SPLIT_GROUP_NAME = "group";
    /**
     * 自定义组名ifstore
     */
    private static final String SPLIT_IFSTORE_NAME = "ifstore";

    private Long currByte;
    private Integer currChunk;
    private Integer totalChunks;

    /**
     * 从Url当中解析存储路径对象
     *
     * @param filePath 有效的路径样式为(group/path) 或者
     *            (http://ip/group/path),路径地址必须包含group
     * @return
     */
    public static StorePath praseFromUrl(String filePath) {
        Validate.notNull(filePath, "解析文件路径不能为空");
        // 获取group起始位置
        int groupStartPos = getGroupStartPos(filePath);
        String groupAndPath = filePath.substring(groupStartPos);

        int pos = groupAndPath.indexOf(SPLIT_GROUP_NAME_AND_FILENAME_SEPARATOR);
        if ((pos <= 0) || (pos == groupAndPath.length() - 1)) {
            throw new FdfsUnsupportStorePathException("解析文件路径错误,有效的路径样式为(group/path) 而当前解析路径为".concat(filePath));
        }

        String group = groupAndPath.substring(0, pos);
        String path = groupAndPath.substring(pos + 1);
        return new StorePath(group, path);
    }

    /**
     * 获得group起始位置
     *
     * @param filePath 文件路径
     */
    private static int getGroupStartPos(String filePath) {
        int pos = filePath.indexOf(SPLIT_GROUP_NAME);
        if ((pos == -1)) {
            pos = filePath.indexOf(SPLIT_IFSTORE_NAME);
            if ((pos == -1)) {
                throw new FdfsUnsupportStorePathException("解析文件路径错误,被解析路径url没有group或ifstore,当前解析路径为".concat(filePath));
            }
        }
        return pos;
    }



}

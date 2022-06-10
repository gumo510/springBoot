package com.gumo.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gumo.demo.dto.vo.UserVO;
import com.gumo.demo.entity.User;
import com.gumo.demo.entity.UserReq;
import com.gumo.demo.mapper.UserMapper;
import com.gumo.demo.service.IUserService;
import com.gumo.demo.utils.ExcelUtil;
import com.gumo.demo.utils.FastClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private FastClientWrapper fastClientWrapper;

    @Value(value = "${file.url.prefix:http://192.168.11.103:8080}")
    public String fileUrl;


    @Async
    @Override
    public String getUserExport() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            List<UserVO> users = userMapper.queryUserList();
            EasyExcel.write(byteArrayOutputStream, UserVO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet().doWrite(users);
        } catch (Exception e) {
            log.error("getUserExport_error", e);
        }
//        byte[] backBytes = Base64Utils.decodeFromString(encryptData); //Base64图片
        byte[] bytes = byteArrayOutputStream.toByteArray();
//        String fileUrl = fastClientWrapper.uploadFile(bytes, "xlsx", null);
        return fileUrl;
    }
}

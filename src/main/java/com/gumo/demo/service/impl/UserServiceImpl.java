package com.gumo.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gumo.demo.model.dto.CommonResult;
import com.gumo.demo.model.vo.UserDeviceVO;
import com.gumo.demo.model.vo.UserVO;
import com.gumo.demo.entity.User;
import com.gumo.demo.lock.annonation.DistributedLock;
import com.gumo.demo.mapper.UserMapper;
import com.gumo.demo.service.IUserService;
import com.gumo.demo.utils.FastClientWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

    @Autowired
    private RedissonClient redissonClient;

    @Value(value = "${file.url.prefix:http://192.168.11.103:8080}")
    public String prefix;

    @Override
    public CommonResult getUserExport() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            List<UserVO> users = userMapper.queryUserList();
            EasyExcel.write(byteArrayOutputStream, UserVO.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())  //自动设置行宽
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("模板").doWrite(users);
        } catch (Exception e) {
            log.error("getUserExport_error", e);
        }
//        byte[] backBytes = Base64Utils.decodeFromString(encryptData); //Base64图片
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String fileUrl = prefix + '/' + fastClientWrapper.uploadFile(bytes, "xlsx", null);
        return CommonResult.success(fileUrl);
    }

    @Override
    public CommonResult getUserExport2() {
        // 方法3 如果写到不同的sheet 不同的对象
        String fileName = System.getProperty("user.dir") + File.separator +"/doc/测试多个Sheet导出.xlsx";;
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class
                // 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(UserVO.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<UserVO> users = userMapper.queryUserList();
                excelWriter.write(users, writeSheet);
            }
        }
        return CommonResult.success(null);
    }

    @Override
    public UserDeviceVO getUserDevice(String login, String password) {
        return userMapper.getUserDevice(login, password);
    }

    @Async                                                                                                                  //异步执行
    @DistributedLock(lockNamePre = "Statistics_Task", argNum = 2, leaseTime = -1, fairLock = true, lockNamePost = "update") //分布式锁注解样例
    public void saveUser() throws Exception{
        User user = new User();
        userMapper.insert(user);
//        String key = String.format("data-mining:task_statistic:%d", System.currentTimeMillis());
//        RLock rLock = redissonClient.getLock(key);
//        try {
//            // 尝试加锁，最多等待1秒，上锁以后10秒自动解锁,没有Watch Dog,10s后自动释放
//            boolean res = rLock.tryLock(1, 10, TimeUnit.SECONDS);
//            if (!res) {
//                System.out.println("请勿重复提交");
//            }
//            baseTypeMapper.updateByBusId(busId, date);
//        } finally {
//            rLock.unlock();
//        }
    }
}

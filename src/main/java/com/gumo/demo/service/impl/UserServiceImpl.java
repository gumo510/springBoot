package com.gumo.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gumo.demo.dto.vo.CommonResult;
import com.gumo.demo.dto.vo.UserVO;
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
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet().doWrite(users);
        } catch (Exception e) {
            log.error("getUserExport_error", e);
        }
//        byte[] backBytes = Base64Utils.decodeFromString(encryptData); //Base64图片
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String fileUrl = prefix + '/' + fastClientWrapper.uploadFile(bytes, "xlsx", null);
        return CommonResult.success(fileUrl);
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

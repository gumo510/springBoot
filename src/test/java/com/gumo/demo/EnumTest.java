package com.gumo.demo;

import com.gumo.demo.enums.CameraInfoOnlineEnum;
import com.gumo.demo.enums.DeviceInfoStatusEnum;
import org.junit.jupiter.api.Test;

/**
 *  策略枚举映射测试类
 */
public class EnumTest {

    @Test
    public void test() {

    }

    public static void main(String[] args) {

        Integer status = 2;
        // 获取对应其他系统状态
        if(DeviceInfoStatusEnum.valueOf(status) != null){
            CameraInfoOnlineEnum otherStatusEnum = DeviceInfoStatusEnum.valueOf(status).getCameraInfoOnlineEnum();
            System.out.println("其他系统的状态为：" + otherStatusEnum.getIndex());
            System.out.println("其他系统的状态为：" + otherStatusEnum.getName());
        }
        System.out.println("其他系统的状态为:" + DeviceInfoStatusEnum.valueOf(status));
    }
}

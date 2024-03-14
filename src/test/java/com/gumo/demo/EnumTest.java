package com.gumo.demo;

import com.gumo.demo.entity.ResultCodeEnum;
import com.gumo.demo.enums.CameraInfoOnlineEnum;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.enums.DeviceInfoStatusEnum;
import com.gumo.demo.service.IResultCodeEnumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 *  策略枚举映射测试类
 */
@SpringBootTest
public class EnumTest {

    @Autowired
    private IResultCodeEnumService resultCodeEnumService;

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

    @Test
    public void uploadResultCodeEnum() {

        List<ResultCodeEnum> resultCodeList = new ArrayList();
        for (CommonResultCodeEnum resultCodeEnum : CommonResultCodeEnum.values()) {
            ResultCodeEnum resultCode = new ResultCodeEnum();
            resultCode.setResultCode(resultCodeEnum.getResultCode());
            resultCode.setName(resultCodeEnum.getName());
            resultCode.setMessage(resultCodeEnum.getMessage());

            resultCodeList.add(resultCode);
        }

        resultCodeEnumService.saveBatch(resultCodeList);
    }

}

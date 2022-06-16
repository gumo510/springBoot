package com.gumo.demo.job;

import com.gumo.demo.utils.HttpUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
public class MileageXxlJob {
    private static Logger logger = LoggerFactory.getLogger(MileageXxlJob.class);

    @Value(value = "${call.job.url:http://127.0.0.1:32056/call/work}")
    private String url;

    @Value(value = "${oracle.job.url:http://127.0.0.1:32031}")
    private String oracleUrl;

    /**
     * 区域设备表数据推送共享中心
     */
    @XxlJob("syncAreaDeviceJobHandler")
    public ReturnT<String> syncAreaDevice(String param) throws Exception {
        XxlJobHelper.log("start syncAreaDevice job");
        for (int i = 0; i < 5; i++) {
            System.out.println("测试执行器使用：" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 设备表数据推送共享中心
     */
    @XxlJob("syncDeviceInfoJobHandler")
    public ReturnT<String> syncDeviceInfo(String param) throws Exception {
        XxlJobHelper.log("start syncDeviceInfo job");
        HttpUtil.get(oracleUrl.concat("/sync/deviceInfo"), null);
        return ReturnT.SUCCESS;
    }

    /**
     * 设备在线率数据推送共享中心
     */
    @XxlJob("syncStatisticDashboardJobHandler")
    public ReturnT<String> syncStatisticDashboard(String param) throws Exception {
        XxlJobHelper.log("start syncStatisticDashboard job");
        HttpUtil.get(oracleUrl.concat("/sync/statisticDashboard"), null);
        return ReturnT.SUCCESS;
    }

}

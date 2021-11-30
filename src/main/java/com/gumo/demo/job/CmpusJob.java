package com.gumo.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmpusJob {

    //每分钟执行一次
    @Scheduled(cron = "${campus.job.cron:0 * * * * *}")
    public void checkAndPassauth() {
         log.info("start persons check and passauth!");

        //检查并重新推送日志
        checkHikPersonAuthLog();

        log.info("finish persons check and passauth!");
    }

    public void checkHikPersonAuthLog() {
        log.info("checkHikPersonAuthLog start!");
/*        List<HikPersonAuthLog> hikPersonAuthLogs = hikPersonAuthLogMapper.selectList(new QueryWrapper<HikPersonAuthLog>().eq("operate_status", -1));
        if(CollectionUtils.isNotEmpty(hikPersonAuthLogs)) {
            for (HikPersonAuthLog hikPersonAuthLog : hikPersonAuthLogs) {
                //重新推送
                if ("add".equals(hikPersonAuthLog.getOperateType())) {
                    //0.图片下发
                    boolean authAddResp = hikService.authoriseAdd(hikPersonAuthLog);
                    log.info("syncHikPersonAuth hikPersonAuthReq: {}, authClearResp: {}",hikPersonAuthLog, authAddResp);
                    hikPersonAuthLog.setOperateStatus(authAddResp ? 0 : -1);
                }if ("clear".equals(hikPersonAuthLog.getOperateType())) {
                    //1.清除授权
                    boolean authClearResp = hikService.authoriseClear();
                    log.info("checkHikPersonAuthLog hikPersonAuthReq: {}, authClearResp: {}",hikPersonAuthLog, authClearResp);
                    hikPersonAuthLog.setOperateStatus(authClearResp ? 0 : -1);
                }else if ("delete".equals(hikPersonAuthLog.getOperateType())) {
                    //2.删除授权
                    boolean authDeleteResp = hikService.authoriseDelete(hikPersonAuthLog.getPersonNo());
                    log.info("checkHikPersonAuthLog hikPersonAuthReq: {}, authDeleteResp: {}",hikPersonAuthLog, authDeleteResp);
                    hikPersonAuthLog.setOperateStatus(authDeleteResp ? 0 : -1);
                }
                //3.更新授权日志
                hikPersonAuthLog.setUpdated(new Date());
                hikPersonAuthLogMapper.updateById(hikPersonAuthLog);
            }
        }*/
        log.info("checkHikPersonAuthLog finished!");
    }
}

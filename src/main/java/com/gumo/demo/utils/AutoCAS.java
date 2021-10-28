package com.gumo.demo.utils;

public class AutoCAS {
    /**
     * 自动派单
     * 只查出一条    返回list只是为了和查询接口统一
     * 视频审核订单不派送
     * @param paramMap
     * @return
     */
/*    public List<AutomaticAssignDto> automaticAssign(Map<String, Object> paramMap){
        //派送规则
        String changeSortSet = RedisCacheUtil.getValue(CACHE_TYPE.APP, "changeSortSet");
        if (StringUtils.isBlank(changeSortSet)) {
            changeSortSet = customerManager.getDictionaryByCode("changeSortSet");
            if (StringUtils.isNotBlank(changeSortSet)) {
                RedisCacheUtil.addValue(CACHE_TYPE.APP, "changeSortSet", changeSortSet,30,TimeUnit.DAYS);
            } else {
                changeSortSet = ConstantsUtil.AssignRule.FIFO; // 默认先进先审
            }
        }
        AutomaticAssignDto automaticAssignDto = new AutomaticAssignDto();
        automaticAssignDto.setChangeSortSet(changeSortSet);
        automaticAssignDto.setUserTeam(CommonUtils.getValue(paramMap, "userTeam"));
        List<AutomaticAssignDto> waitCheckList = automaticAssignMybatisDao.automaticAssignOrder(automaticAssignDto);
        if(waitCheckList != null && waitCheckList.size()>0){
            automaticAssignDto = waitCheckList.get(0);
            automaticAssignDto.setSendStatus(ConstantsUtil.SendStatus.SEND);
            automaticAssignDto.setBindTime(new Date());
            automaticAssignDto.setUserId(Long.parseLong(paramMap.get("userId").toString()) );
            int sum = automaticAssignMybatisDao.bindAutomaticAssignInfo(automaticAssignDto);
            if(sum == 1){
                return waitCheckList;
            }else{
                //已被更新 则再次获取
                return automaticAssign(paramMap);
            }
        }else{
            return null;
        }
    }*/
}


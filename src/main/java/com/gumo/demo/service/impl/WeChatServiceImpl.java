package com.gumo.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gumo.demo.config.WeChatConfigure;
import com.gumo.demo.enums.CommonResultCodeEnum;
import com.gumo.demo.model.dto.DataItem;
import com.gumo.demo.model.dto.Weather;
import com.gumo.demo.model.vo.BaseDataRespVo;
import com.gumo.demo.model.vo.ResultVO;
import com.gumo.demo.service.IWeChatService;
import com.gumo.demo.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
public class WeChatServiceImpl implements IWeChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatConfigure weChatConfigure;

    /**
     * {{date.DATA}}
     * {{remark.DATA}}
     * 所在城市：{{city.DATA}}
     * 今日天气：{{weather.DATA}}
     * 气温变化：{{min_temperature.DATA}} ~ {{max_temperature.DATA}}
     * 今日建议：{{tips.DATA}}
     * 今天是我们恋爱的第 {{love_days.DATA}} 天
     * 距离xx生日还有 {{girl_birthday.DATA}} 天
     * 距离xx生日还有 {{boy_birthday.DATA}} 天
     * {{rainbow.DATA}}
     */
    @Override
    public BaseDataRespVo sendWeChatMessage(){
        ResultVO resultVo = ResultVO.initializeResultVo(weChatConfigure.Open_ID,weChatConfigure.Template_ID,weChatConfigure.Top_Color);
        //1.设置城市与天气信息
        Weather weather = getWeather(restTemplate);
        resultVo.setAttribute("date",new DataItem(weather.getDate() + " " + weather.getWeek(),"#00BFFF"));
        resultVo.setAttribute("city",new DataItem(weather.getArea(),null));
        resultVo.setAttribute("weather",new DataItem(weather.getWeather(),"#1f95c5"));
        resultVo.setAttribute("min_temp",new DataItem(weather.getLowest(),"#0ace3c"));
        resultVo.setAttribute("max_temp",new DataItem(weather.getHighest(),"#dc1010"));
        resultVo.setAttribute("tips",new DataItem(weather.getTips(),null));
        //2.设置日期相关
        int love_days = DataUtils.getLoveDays(weChatConfigure.Love_Day);
        int girl_birthday = DataUtils.getBirthDays(weChatConfigure.Girl_Birthday);
        int boy_birthday = DataUtils.getBirthDays(weChatConfigure.Boy_Birthday);
        resultVo.setAttribute("love_days",new DataItem(love_days+"","#FFA500"));
        resultVo.setAttribute("girl_birthday",new DataItem(girl_birthday+"","#FFA500"));
        resultVo.setAttribute("boy_birthday",new DataItem(boy_birthday+"","#FFA500"));
        //3.设置彩虹屁
        String rainbow = getRainbow(restTemplate);
        resultVo.setAttribute("rainbow",new DataItem(rainbow,"#FF69B4"));
        //4.其他
        String remark = "❤";
        if(DataUtils.getBirthDays(weChatConfigure.Love_Day) == 0){
            remark = "今天是恋爱周年纪念日！永远爱你~";
        }else if(girl_birthday == 0){
            remark = "今天是婉婉宝贝的生日！生日快乐哟~";
        }else if(boy_birthday == 0){
            remark = "今天是主人的生日！别忘了好好爱他~";
        }
        resultVo.setAttribute("remark",new DataItem(remark,"#FF1493"));
        //5.发送请求，推送消息
        log.info("WeChatServiceImpl_push url:{},params:{}",weChatConfigure.Send_URL, JSONObject.toJSONString(resultVo));
        String responseStr = restTemplate.postForObject(weChatConfigure.Send_URL, resultVo, String.class, getAccessToken(restTemplate));
        log.info("WeChatServiceImpl_push: {}",JSONObject.toJSONString(responseStr));
        return new BaseDataRespVo(responseStr, CommonResultCodeEnum.SUCCESS.getResultCode(), "成功", "SUCCESS");
    }

    public String getAccessToken(RestTemplate restTemplate) {
        // 构建请求地址
        String url = String.format(weChatConfigure.Access_URL, weChatConfigure.App_ID, weChatConfigure.App_Secret);
        // 获取token
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        log.info("WeChatServiceImpl_getAccessToken: {}",JSONObject.toJSONString(responseEntity));
        JSONObject jsonObject = responseEntity.getBody();
        return jsonObject.getString("access_token");
    }

    /**
     * 获取 Weather 信息
     * @param restTemplate
     * @return
     */
    public Weather getWeather(RestTemplate restTemplate){
        String responseJson = restTemplate.getForObject(weChatConfigure.Weather_API, String.class);
        JSONObject responseResult = JSONObject.parseObject(responseJson);
        JSONObject jsonObject = responseResult.getJSONObject("result");
        return jsonObject.toJavaObject(Weather.class);
    }

    /**
     * 获取 RainbowPi 信息
     * @param restTemplate
     * @return
     */
    public String getRainbow(RestTemplate restTemplate){
        String responseJson = restTemplate.getForObject(weChatConfigure.Rainbow_API, String.class);
        JSONObject responseResult = JSONObject.parseObject(responseJson);
        JSONObject jsonObject = responseResult.getJSONObject("result");
        return jsonObject.getString("content");
    }
}

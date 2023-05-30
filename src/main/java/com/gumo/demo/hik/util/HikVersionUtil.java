package com.gumo.demo.hik.util;

import com.gumo.demo.hik.config.HikVersionConfig;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 海康接口工具类
 */
@Slf4j
public class HikVersionUtil {

    public static String getHikDataByPost(String uri, String json) {
        String url = HikVersionConfig.ARTEMIS_PATH + uri;
        log.info("HikVersionUtil_hik api: url {}", url);
        Map<String, String> path = new HashMap<String, String>() {
            { put("https://", url); }
        };
        String ret = ArtemisHttpUtil.doPostStringArtemis(path, json, null, null, "application/json");
        log.info("HikVersionUtil_getHikDataByPost path: {}, json: {}, ret: {}", path, json, ret);
        return ret;
    }
}

package com.gumo.demo.feign.fallback;

import com.gumo.demo.feign.TestFeignClientServices;
import org.springframework.stereotype.Component;

/**
 * @author gumo
 */
@Component
public class TestFeignClientServicesFallBack implements TestFeignClientServices {

    @Override
    public String get(String name) {
        return "服务器繁忙-请稍后重试" ;
    }

    @Override
    public String testPutStringToMq(String name) {
        return "服务器繁忙-请稍后重试" ;
    }


}

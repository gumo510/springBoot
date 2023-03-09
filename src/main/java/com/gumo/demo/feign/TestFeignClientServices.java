package com.gumo.demo.feign;

import com.gumo.demo.feign.fallback.TestFeignClientServicesFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gumo
 */
@FeignClient(name = "server-service", url = "127.0.0.1:8080", fallback = TestFeignClientServicesFallBack.class)
public interface TestFeignClientServices {

    @RequestMapping(value = "/test/get", method = RequestMethod.POST)
    String get(@RequestParam String name);

    @GetMapping("/testFegin")
    String testPutStringToMq(@RequestParam (value = "id") String id);

}

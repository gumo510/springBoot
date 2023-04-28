package com.gumo.demo.feign.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.Map;

public class WxworkClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Map<String, Collection<String>> headers = template.headers();
        headers.forEach((key, value) -> {
            if (key.equals("Content-Type")) {
                value.clear();
                value.add("application/json");
            }
        });
    }
}

package com.gumo.demo.enums;

import org.apache.commons.lang3.StringUtils;

public enum SmsPlatfromEnum {
    /**
     * 短信平台枚举
     */
    ALIBABA_CLOUD("ALIYUN", "阿里云"),
    CHINA_MOBILE("YIDONG", "移动"),
    ;

    private String type;

    private String name;

    SmsPlatfromEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SmsPlatfromEnum getByTyoe(String type) {
        if (StringUtils.isNotEmpty(type)) {
            for (SmsPlatfromEnum smsPlatfromEnumenum : SmsPlatfromEnum.values()) {
                if (type.equals(smsPlatfromEnumenum.getType())) {
                    return smsPlatfromEnumenum;
                }
            }
        }
        return ALIBABA_CLOUD;
    }
}

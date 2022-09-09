package com.gumo.demo.enums;

/**
 * <p>
 *
 * </p>
 *
 * @author gumo
 * @since 2021-07-04 15:30
 */
public enum ColorCrowedEnum {

    /**
     * colorCrowed字段，颜色枚举
     */
    DEFAULT(0),
    GREEN(1),
    BLUE(2),
    ORANGE(3),
    RED(4),
    ;

    private final int value;

    ColorCrowedEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

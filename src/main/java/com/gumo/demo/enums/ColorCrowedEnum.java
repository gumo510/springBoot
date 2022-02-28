package com.gumo.demo.enums;

/**
 * <p>
 *
 * </p>
 *
 * @author pbb
 * @since 2021-07-04 15:30
 */
public enum ColorCrowedEnum {
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

package com.gumo.demo.enums;

/**
 * @author Evan
 * @ClassName TableTypeEnum
 * @date 2022/4/19 14:19
 * @Version 1.0.0
 * @Description 分区大小图枚举类
 **/
public enum TableTypeEnum {
    BIG_IMAGE(1, "big_image"),
    SMALL_IMAGE(2, "small_image");

    private Integer tType;
    private String tTypeName;

    TableTypeEnum(Integer tType, String tTypeName) {
        this.tType = tType;
        this.tTypeName = tTypeName;
    }

    public static Integer getType(String value) {
        for (TableTypeEnum tableTypeEnum : TableTypeEnum.values()) {
            if (value.equals(tableTypeEnum.tTypeName)) {
                return tableTypeEnum.tType;
            }
        }
        return null;
    }

    public static String getName(Integer key) {
        for (TableTypeEnum tableTypeEnum : TableTypeEnum.values()) {
            if (tableTypeEnum.tType == key) {
                return tableTypeEnum.tTypeName;
            }
        }
        return null;
    }
}

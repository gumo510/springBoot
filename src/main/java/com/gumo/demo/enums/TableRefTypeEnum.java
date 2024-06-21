package com.gumo.demo.enums;

/**
 * @author Evan
 * @ClassName TableRefTypeEnum
 * @date 2022/4/19 14:19
 * @Version 1.0.0
 * @Description 分区表类型枚举类
 **/
public enum TableRefTypeEnum {
    //目标类型
    FACE_IMAGE(1, "t_face"),
    CAR_IMAGE(2, "t_car"),
    BIKE_IMAGE(3, "t_bike"),
    EVENT_IMAGE(4, "t_event");

    private Integer tRefType;
    private String tRefTypeName;

    TableRefTypeEnum(Integer tRefType, String tRefTypeName) {
        this.tRefType = tRefType;
        this.tRefTypeName = tRefTypeName;
    }

    public static Integer getRefType(String value) {
        for (TableRefTypeEnum tableRefTypeEnum : TableRefTypeEnum.values()) {
            if (value.equals(tableRefTypeEnum.tRefTypeName)) {
                return tableRefTypeEnum.tRefType;
            }
        }
        return null;
    }

    public static String getRefName(Integer key) {
        for (TableRefTypeEnum tableRefTypeEnum : TableRefTypeEnum.values()) {
            if (tableRefTypeEnum.tRefType == key) {
                return tableRefTypeEnum.tRefTypeName;
            }
        }
        return null;
    }
}

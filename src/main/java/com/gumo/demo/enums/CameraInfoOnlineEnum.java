package com.gumo.demo.enums;

/**
 * 设备在线状态
 */
public enum CameraInfoOnlineEnum {

    /** 未知 */
    UNKNOWN(0, "未知") {},

    /** 禁用 */
    DISABLE(1, "禁用") {
        @Override
        public DeviceInfoStatusEnum getDeviceInfoStatusEnum() {
            return DeviceInfoStatusEnum.INACTIVE;
        }
    },

    /** 在线 */
    ONLINE(2, "在线") {
        @Override
        public DeviceInfoStatusEnum getDeviceInfoStatusEnum() {
            return DeviceInfoStatusEnum.ONLINE;
        }
    },

    /** 断线 */
    OFFLINE(3, "断线") {
        @Override
        public DeviceInfoStatusEnum getDeviceInfoStatusEnum() {
            return DeviceInfoStatusEnum.OFFLINE;
        }
    };

    /** 获取其他系统 */
    public DeviceInfoStatusEnum getDeviceInfoStatusEnum() {
        throw new RuntimeException("当前状态【" + this.getName() + "】没有对应的防疫设备在线状态");
    }

    public static CameraInfoOnlineEnum valueOf(Integer index) {
        if (index == null) {
            return null;
        }
        for (CameraInfoOnlineEnum modeEnum : CameraInfoOnlineEnum.values()) {
            if (index == modeEnum.getIndex()) {
                return modeEnum;
            }
        }
        return null;
    }

    /** 枚举值 */
    private int index;

    /** 枚举描述 */
    private String name;

    CameraInfoOnlineEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
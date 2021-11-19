package com.gumo.demo.enums;

/**
 * 防疫设备在线状态
 */
public enum DeviceInfoStatusEnum {

    /** 在线 */
    ONLINE(1, "在线") {
        @Override
        public CameraInfoOnlineEnum getCameraInfoOnlineEnum() {
            return CameraInfoOnlineEnum.ONLINE;
        }
    },

    /** 离线 */
    OFFLINE(0, "离线") {
        @Override
        public CameraInfoOnlineEnum getCameraInfoOnlineEnum() {
            return CameraInfoOnlineEnum.OFFLINE;
        }
    },

    /** 未激活 */
    INACTIVE(-1, "未激活") {
        @Override
        public CameraInfoOnlineEnum getCameraInfoOnlineEnum() {
            return CameraInfoOnlineEnum.DISABLE;
        }
    };

    /** 获取当前系统 */
    public CameraInfoOnlineEnum getCameraInfoOnlineEnum() {
        throw new RuntimeException("当前状态【" + this.getName() + "】没有对应的设备在线状态");
    }

    public static DeviceInfoStatusEnum valueOf(Integer index) {
        if (index == null) {
            return null;
        }
        for (DeviceInfoStatusEnum modeEnum : DeviceInfoStatusEnum.values()) {
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

    DeviceInfoStatusEnum(int index, String name) {
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

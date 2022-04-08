package com.gumo.demo.enums;

/**
 * 摄像头在线状态（0-未知 1-禁用 2-在线 3-断线）
 *
 * @author lx362
 */
public enum CameraOnlineEnum {

	/**
	 * online字段，在线状态
	 */
	UNKNOWN("未知", 0),
	DISABLE("禁用", 1),
	ONLINE("在线", 2),
	OFFLINE("断线", 3);

	/**
	 * 成员变量
	 */
	private String name;
	private Integer value;

	/**
	 * 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
	 * @param name
	 * @param value
	 */
	CameraOnlineEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 普通方法
	 * @param value
	 * @return
	 */
	public static String getName(Integer value) {
		if(value!=null){
			for (CameraOnlineEnum c : CameraOnlineEnum.values()) {
				if (c.getValue().compareTo(value) == 0) {
					return c.name;
				}
			}
		}
		return null;
	}

	public static Integer getValue(String name) {
		if(name != null) {
			for (CameraOnlineEnum c : CameraOnlineEnum.values()) {
				if (c.getName().equals(name)) {
					return c.value;
				}
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}

}

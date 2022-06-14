package com.gumo.demo.enums;

/**
 * 操作类型 0-新增 1-更新 2-删除
 *
 * @author lx362
 */
public enum OperateTypeEnum {

	/**
	 * OperateType，操作类型
	 */
	ADD("新增", 0),
	UPDATE("更新", 1),
	DELETE("删除", 2);

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
	OperateTypeEnum(String name, Integer value) {
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
			for (OperateTypeEnum c : OperateTypeEnum.values()) {
				if (c.getValue().compareTo(value) == 0) {
					return c.name;
				}
			}
		}
		return null;
	}

	public static Integer getValue(String name) {
		if(name != null) {
			for (OperateTypeEnum c : OperateTypeEnum.values()) {
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

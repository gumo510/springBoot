package com.gumo.demo.enums;

/**
 * 记录类型,1新增,2删除,3修改
 *
 * @author
 */
public enum PassAuthPersonTypeEnum {

	/**
	 * OperateType，操作类型
	 */
	ADD("1新增临时访客", 1),
	UPDATE("2删除", 2),
	DELETE("3修改", 3);

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
	PassAuthPersonTypeEnum(String name, Integer value) {
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
			for (PassAuthPersonTypeEnum c : PassAuthPersonTypeEnum.values()) {
				if (c.getValue().compareTo(value) == 0) {
					return c.name;
				}
			}
		}
		return null;
	}

	public static Integer getValue(String name) {
		if(name != null) {
			for (PassAuthPersonTypeEnum c : PassAuthPersonTypeEnum.values()) {
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

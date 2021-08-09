package com.qzsoft.tah.enums;

public enum AlarmTypeEnum {

	CGWD("CGWD", "超高温度"),
	CDWD("CDWD", "超低温度"),
	CGSD("CGSD", "超高湿度"),
	CDSD("CDSD", "超低温度"),
	SBYC("SBYC", "设备异常");

	private String code;
	private String desc;

	AlarmTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getCodeByName(String name) {
		AlarmTypeEnum[] values = AlarmTypeEnum.values();
		for (AlarmTypeEnum value : values) {
			if (value.name().equals(name)) {
				return value.getCode();
			}
		}
		return null;
	}

	public static String getCodeByDesc(String desc) {
		AlarmTypeEnum[] values = AlarmTypeEnum.values();
		for (AlarmTypeEnum value : values) {
			if (value.getDesc().equals(desc)) {
				return value.getCode();
			}
		}
		return null;
	}

	public static String getDescByCode(String code) {
		AlarmTypeEnum[] values = AlarmTypeEnum.values();
		for (AlarmTypeEnum value : values) {
			if (value.getCode().equals(code)) {
				return value.getDesc();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

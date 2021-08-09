package com.qzsoft.tah.enums;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
public enum DevTypeEnum {

    WD("WD", "温度"),
    SD("SD", "湿度"),
    WSD("WSD", "温湿度");

    private String code;
    private String desc;

    DevTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getCodeByDesc(String desc) {
        DevTypeEnum[] values = DevTypeEnum.values();
        for (DevTypeEnum value : values) {
            if (value.getDesc().equals(desc)) {
                return value.getCode();
            }
        }
        return null;
    }

    public static String getDescByCode(String code) {
        DevTypeEnum[] values = DevTypeEnum.values();
        for (DevTypeEnum value : values) {
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

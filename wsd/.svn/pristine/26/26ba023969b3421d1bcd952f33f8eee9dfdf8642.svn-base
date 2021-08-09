package com.qzsoft.tah.enums;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
public enum DevNetStEnum {

    WLZT_LX("WLZT_LX", "离线"),
    WLZT_ZX("WLZT_ZX", "在线");

    private String code;
    private String desc;

    DevNetStEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static String getCodeByDesc(String desc) {
        DevNetStEnum[] values = DevNetStEnum.values();
        for (DevNetStEnum value : values) {
            if (value.getDesc().equals(desc)) {
                return value.getCode();
            }
        }
        return null;
    }

    public static String getDescByCode(String code) {
        DevNetStEnum[] values = DevNetStEnum.values();
        for (DevNetStEnum value : values) {
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

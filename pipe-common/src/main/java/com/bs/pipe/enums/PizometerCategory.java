package com.bs.pipe.enums;

public enum PizometerCategory {

    NORMAL_PIPE("10","常规管网"),
    FIRE_PIPE("20","消防管网"),
    COME_PIPE("30","进水管网");

    private String code;

    private String msg;

    PizometerCategory(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

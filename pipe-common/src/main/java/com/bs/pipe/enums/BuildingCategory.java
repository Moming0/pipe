package com.bs.pipe.enums;

public enum BuildingCategory {

    BUILDING_BOTTOM("0","建筑底层"),
    BUILDING_TOP("1","建筑顶层");

    private String code;

    private String msg;

    BuildingCategory(String code, String msg) {
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

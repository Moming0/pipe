package com.bs.pipe.enums;

public enum Category {

    PIEZOMETER("10","压力点"),
    BUILDING("20","建筑一层"),
    TOPBUILDING("30","建筑顶层"),
    WATERREGION("40","区域"),
    GRIDNODE("50","网格点");

    private String code;

    private String msg;

    Category(String code, String msg) {
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

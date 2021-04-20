package com.bs.pipe.enums;

public enum StatisticsUnit {

    INITIAL_UNIT("-1","原始数据"),
    HOUR_UNIT("0","小时"),
    DAY_UNIT("1","日");

    private String code;

    private String msg;

    StatisticsUnit(String code, String msg) {
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

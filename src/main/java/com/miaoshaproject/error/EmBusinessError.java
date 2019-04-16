package com.miaoshaproject.error;

public enum EmBusinessError implements CommonError {

    //General Err type:10001
    PARAMETER_VALIDATION_ERROR(10001, "Illegal Parameter"),
    UNKNOWN_ERROR(10002, "Unknown Error"),


    //20000 User Error related
    USER_NOT_EXIST(20001, "User Missed"),
    USER_LOGIN_FAIL(20002, "Telephone Number Or Password Not Correct"),
    USER_NOT_LOGIN(20003,"You have not login!"),


    //30000 Order info Error
    STOCK_NOT_ENOUGH(30001,"Stock Insufficient"),

    ;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}

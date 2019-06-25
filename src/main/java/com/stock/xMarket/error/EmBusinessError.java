package com.stock.xMarket.error;

public enum EmBusinessError implements CommonError {

    // 通用错误类型
    UNKNOWN_ERROR(10000,"未知的错误"),
    OBJECT_ALREADY_EXIST_ERROR(10001,"要创建的对象已存在"),
    VALIDATION_ERROR(10002,"验证失败"),
    OBJECT_NOT_EXIST_ERROR(10003,"要获取的对象不存在"),


    //50000开头为业务相关错误定义
    VERIFICATION_CODE_FAIL_ERROR(50001,"获取验证码失败"),
    RESET_PASSWORD_FAIL_ERROR(50002,"重置密码失败"),
    USERID_NOT_EXIST_ERROR(50003,"用户不存在"),
    EMAIL_NOT_EXIST_ERROR(50004,"邮箱不存在"),
    FUND_ERROR(50005,"资金不足"),
    ORDER_FINISH_ERROR(50006,"委托已完成"),
    ILLEGAL_TIME_ERROR(50007,"非法的交易时间"),
    EMAIL_EXIST_ERROR(50008,"该邮箱已被注册！"),
    USERNAME_EXIST_ERROR(50008,"该用户名已被使用！"),
    ILLEGAL_PRICE_ERROR(50009,"非法的价格！")
    ;

    EmBusinessError(int errCode,String errMsg){
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

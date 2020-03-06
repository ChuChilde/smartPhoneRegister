package com.hardy.fleamarket.error;

/**
 * 自定义返回前端的错误码和对应的信息
 */
public enum EnumError implements CommonError {
    COMMON_ERROR(0001, "通用错误码"),
    SYSTEM_ERROR(1000, "系统错误"),
    ESECODE_ERROR(1001,"验证码错误"),
    USER_NOT_EXIST(1002, "用户不存在"),
    USER_HAVE_EXIST(1003, "用户已经存在"),
    REGISTER_FAIL(1004,"失败，请重试"),
    SENDSMS_FAIL(1005,"短信码发送失败"),
    PHONE_ERROR(1006,"手机号码不正确或者不可用"),
    PARANETER_FORMAT_ERROR(1007,"参数格式不合法"),
    PARANETER_NOT_ALL(1008,"参数不全"),
    PASSWORD_ERROR(1009,"密码错误"),
    LOGIN_OVERDUE(1010,"登录过期了");

    EnumError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private int errorCode;

    private String errorMessage;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}

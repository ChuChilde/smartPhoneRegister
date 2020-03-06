package com.hardy.fleamarket.service.serviceexception;

public enum ExceptionIdentifyCode {
    INSERT_USERMESSAGE_EXCEPTION(9001,"用户信息插入异常"),
    INSERT_USERPASSWORD_EXCEPTION(9002,"用户密码插入异常"),
    INSERT_COMMENT_EXCEPTION(9003,"插入评论异常")
    ;

    private int code;
    private String message;

    ExceptionIdentifyCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

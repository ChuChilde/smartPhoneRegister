package com.hardy.fleamarket.service.serviceexception;

public class ServiceCommonException extends Exception{

    private int code;

    private String parameter;

    public ServiceCommonException(ExceptionIdentifyCode codeMessage, String parameter){
        super(codeMessage.getMessage());
        this.code = codeMessage.getCode();
        this.parameter = parameter;
    }

    public int getCode() {
        return code;
    }

    public String getParameter(){
        return parameter;
    }
}

package com.hardy.fleamarket.controller.response;

public class CommonReturnType {

    private String  status;

    private Object data;

    public CommonReturnType(){}
    public CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

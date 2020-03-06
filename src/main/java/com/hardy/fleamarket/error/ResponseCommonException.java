package com.hardy.fleamarket.error;

/**
 * 对通用错误码进行异常包装
 */
public class ResponseCommonException extends Exception implements CommonError {

    private CommonError commonError;

    public ResponseCommonException(CommonError commonError) {
        super(commonError.getErrorMessage());
        this.commonError = commonError;
    }

    public ResponseCommonException(CommonError commonError, String errorMessage) {
        super(errorMessage);
        this.commonError = commonError;
        this.setErrorMessage(errorMessage);
    }

    @Override
    public int getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return commonError.getErrorMessage();
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        commonError.setErrorMessage(errorMessage);
        return this;
    }
}

package com.hardy.fleamarket.error;

/**
 * 定义通用错误接口
 */
public interface CommonError {

    int getErrorCode();

    String getErrorMessage();

    CommonError setErrorMessage(String errorMessage);
}

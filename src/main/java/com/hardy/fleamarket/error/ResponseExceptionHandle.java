package com.hardy.fleamarket.error;

import com.hardy.fleamarket.controller.response.CommonReturnType;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * controller层异常处理类
 */
@ControllerAdvice
public class ResponseExceptionHandle {

    /**
     * 通用异常处理，返回fail和相应的异常信息
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception exception) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        Map<String, Object> returnData = new HashMap();
        //返回自定义异常
        if(exception instanceof ResponseCommonException){
            ResponseCommonException e = (ResponseCommonException)exception;
            returnData.put("errorCode",e.getErrorCode());
            returnData.put("errorMessage",e.getErrorMessage());
            commonReturnType.setData(returnData);
            return commonReturnType;
        }
        //http方法不正确异常处理，返回fail和相应的异常信息
        if(exception instanceof HttpRequestMethodNotSupportedException){
            returnData.put("errorCode","0000");
            returnData.put("errorMessage","http请求方法不正确");
            commonReturnType.setData(returnData);
            return commonReturnType;
        }
        if(exception instanceof MissingServletRequestParameterException){
            returnData.put("errorCode","0000");
            returnData.put("errorMessage","http请求参数不全");
            commonReturnType.setData(returnData);
            return commonReturnType;
        }
        if(exception instanceof NoHandlerFoundException){
            returnData.put("errorCode","0000");
            returnData.put("errorMessage","页面路径不存在");
            commonReturnType.setData(returnData);
            return commonReturnType;
        }
        returnData.put("errorCode","0000");
        returnData.put("errorMessage","未知错误");
        commonReturnType.setData(returnData);
        return commonReturnType;
    }

}

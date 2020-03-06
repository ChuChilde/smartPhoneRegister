package com.hardy.fleamarket.log;

import com.hardy.fleamarket.service.serviceexception.ServiceCommonException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OutputExceptionLogAspect {

    /**
     * 用自定义注释作为切点
     */
    @Pointcut("@annotation(com.hardy.fleamarket.log.OutputExceptionLog)")
    public void pointcut(){}

    /**
     * 切点有异常时触发打印异常日志
     * 当是自定义异常时打印相关触发异常时的参数，否则只打印异常信息描述
     * @param outputExceptionLog
     * @param exceptionMessage
     */
    @AfterThrowing(value = "pointcut()&& @annotation(outputExceptionLog)",throwing = "exceptionMessage")
    public void outputExceptionLog(OutputExceptionLog outputExceptionLog, Exception exceptionMessage){
        String parameter;
        try {
            ServiceCommonException serviceCommonException = (ServiceCommonException) exceptionMessage;
            parameter = serviceCommonException.getParameter();
        }catch (ClassCastException e){
            parameter = null;
        }
        System.out.println("log:"+outputExceptionLog.message()+exceptionMessage.getMessage()+parameter);
    }

}

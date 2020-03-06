package com.hardy.fleamarket.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hardy.fleamarket.error.ResponseCommonException;
import com.hardy.fleamarket.log.OutputExceptionLog;

import java.util.Map;

public class SendSms {

    private static final String accessKeyId = "LTAI4FhG8DVGG5ZHS57imwLC";
    private static final String accessKeySecret = "S52sxNfer4I7XwYjbony7y38AGPmpG";
    private static final String SignName = "hardy个人学习平台";
    private static final String TemplateCode = "SMS_181550753";

    /**
     * 通过阿里云发送短信给注册用户
     * @param phone
     * @param parameter
     * @return
     * @throws ResponseCommonException
     */
    @OutputExceptionLog(message = "通过阿里云发送短信给注册用户")
    public Map sendSms(String phone, Map parameter) throws ClientException {
        JsonUtil jsonUtil = new JsonUtil();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        try {
            request.putQueryParameter("TemplateParam", jsonUtil.mapToJson(parameter));
            CommonResponse response = client.getCommonResponse(request);
            return jsonUtil.jsonToMap(response.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

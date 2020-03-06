package com.hardy.fleamarket.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardy.fleamarket.error.ResponseCommonException;
import com.hardy.fleamarket.log.OutputExceptionLog;

/**
 * 自定义通用json处理类
 * 定义异常处理
 */
public class JsonUtil {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * String的json转为Map抛出CommonException异常提示系统出错
     * @param json
     * @return
     * @throws ResponseCommonException
     */
    @OutputExceptionLog(message = "String的json转为Map")
    public Map jsonToMap(String json) throws JsonProcessingException {
        return MAPPER.readValue(json, Map.class);
    }

    /**
     * Map转为String的json抛出CommonException异常提示系统出错
     * @param map
     * @return
     * @throws ResponseCommonException
     */
    @OutputExceptionLog(message = "Map转为String的json")
    public String mapToJson(Map map) throws JsonProcessingException {
        return MAPPER.writeValueAsString(map);
    }

}

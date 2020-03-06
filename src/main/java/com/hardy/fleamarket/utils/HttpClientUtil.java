package com.hardy.fleamarket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * http请求通用
 *
 */
public class HttpClientUtil {

    /**
     * 发起一次HTTP的GET请求
     * @param url 请求连接
     * @param params  参数集
     * @param headers  请求头集
     * @return   返回一个CloseableHttpResponse对象
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers){
        String apiUrl = url;
        //参数拼接成完整的带参URL
        if(params != null && !params.isEmpty()){
            StringBuffer param = new StringBuffer();
            int i = 0;
            for(String key : params.keySet()){
                if(i == 0 && !apiUrl.endsWith("?")){
                    param.append("?");
                }
                else{
                    param.append("&");
                }
                param.append(key).append("=").append(params.get(key));
                i++;
            }
            apiUrl += param;
        }
        //创建一个连接对象和一个客户端对象
        HttpGet httpGet = new HttpGet(apiUrl);
        CloseableHttpClient httpClient = HttpClients.createDefault();;
        //设置请求头
        if(headers != null && !headers.isEmpty()){
            for(String key : headers.keySet()){
                httpGet.addHeader(key, headers.get(key));
            }
        }
        //发起HTTP请求
        CloseableHttpResponse httpResponse =null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            String response ;
            response  = EntityUtils.toString(entity, "UTF-8");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            System.out.println("下发http接口失败" + e);
        }
        finally {
            try {
                // 关闭连接,释放资源
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpClient.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发起一个HTTP  Post请求，提交json数据
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers){
        String apiUrl = url;
        //创建一个连接对象
        HttpPost httpPost = new HttpPost(apiUrl);
        if(params != null && !params.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            String param = "";
            //利用Jackson将参数转为json字符串
            try {
                param = objectMapper.writeValueAsString(params);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //设置相关json数据的参数
            StringEntity entity = new StringEntity(param,"utf-8");
            entity.setContentEncoding("utf-8");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(entity);
        }
        //设置请求头
        if(headers != null && !headers.isEmpty()){
            for(String key : headers.keySet()){
                httpPost.addHeader(key, headers.get(key));
            }
        }
        //创建HttpClient对象并发起请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String response ;
            response  = EntityUtils.toString(entity, "UTF-8");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭连接,释放资源
                if (httpResponse != null) {
                    httpResponse.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * POST请求表单提交方式
     * @param httpUrl
     * @param maps
     * @param headers
     * @return
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        //设置请求头
        if(headers != null && !headers.isEmpty()){
            for(String key : headers.keySet()){
                httpPost.addHeader(key, headers.get(key));
            }
        }
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }


    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}


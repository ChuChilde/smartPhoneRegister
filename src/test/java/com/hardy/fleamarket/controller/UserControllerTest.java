package com.hardy.fleamarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //初始化MockMvc对象
    }

    @Test
    public void addUser() throws Exception{
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("phone","13125150883");
        parameter.put("password","13125150705");
        parameter.put("name","name");
        parameter.put("headPicture","13125150705");
        parameter.put("gender","1");
        parameter.put("longitude","50");
        parameter.put("latitude","15");
        parameter.put("smsCode","2222");
        parameter.put("content","hryrh突然if");
        parameter.put("token","eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODM0NTQ3NzIsInVpZCI6MTUsImlhdCI6MTU4MzM5NDc3Mn0.KcC--ZQkkX6TqSQfMObtjLuwKXmjiTaM14uM18lX0CcRsKb_aqQXk940AL0RuKzMUkr4Jv3UGlXK2-jEo1wYzw");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(parameter);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/1/report?token=eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODM0NTQ3NzIsInVpZCI6MTUsImlhdCI6MTU4MzM5NDc3Mn0.KcC--ZQkkX6TqSQfMObtjLuwKXmjiTaM14uM18lX0CcRsKb_aqQXk940AL0RuKzMUkr4Jv3UGlXK2-jEo1wYzw")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(parameter))
        )
                .andDo(print()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
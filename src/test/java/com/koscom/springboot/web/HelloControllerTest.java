package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class) // Spring 으로 Test할 것임을 Junit에 알림
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }) // controller 만 테스트 ; HelloController만 테스트
            // SecurityConfig 도 제외


public class HelloControllerTest {
    @Autowired
    MockMvc mvc;

    @WithMockUser(roles = "USER")  // mockmvc 가 있을 때만 사용할 수 있음
    @Test
    void  hello주소로요청이오면_hello가_리턴된다() throws Exception {
        String expectResult = "hello";
        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectResult));
    }

    @WithMockUser(roles = "USER")  // mockmvc 가 있을 때만 사용할 수 있음
    @Test
    void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

    @WithMockUser(roles = "USER")  // mockmvc 가 있을 때만 사용할 수 있음
    @Test
    void amount가없으면_응답코드가400이_된다() throws Exception {
        String name = "hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
                .param("name", name))
                .andExpect(status().isBadRequest());
    }
}

package com.davidgodness.simpletwitter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTestWithMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = "classpath:init/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[0].idName").value("zhangdawei"))
                .andExpect(jsonPath("$.[1].idName").value("davidchang"));
    }

    @Test
    public void testRegisterUserWithIdNameDuplicate() {
        //TODO
    }

    @Test
    public void testRegisterUserWithSuccess() {
        //TODO
    }

    @Test
    public void testUnRegisterUser() {
        //TODO
    }
}

package com.davidgodness.simpletwitter;

import com.davidgodness.simpletwitter.user.UserRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class TestUserControllerWithMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUsersDefaultPageAndSortCreatedTime() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].id").value(3))
                .andExpect(jsonPath("$.[1].id").value(1))
                .andExpect(jsonPath("$.[2].id").value(2))
                .andExpect(jsonPath("$.[0].idName").value("baby"))
                .andExpect(jsonPath("$.[1].idName").value("zhangdawei"))
                .andExpect(jsonPath("$.[2].idName").value("davidchang"));
    }

    @Test
    public void testGetUsersPageAndSort() throws Exception {
        mockMvc.perform(get("/users?page=1&size=1&sort=idName,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].idName").value("davidchang"));
    }

    @Test
    public void testRegisterUserWithIdNameDuplicate() throws Exception {
        String requestBody = Files.readString(Paths.get("src", "test", "resources", "duplicateIdName.json"));

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    public void testRegisterUserWithSuccess() throws Exception {
        String requestBody = Files.readString(Paths.get("src", "test", "resources", "newUser.json"));

        DocumentContext context = JsonPath.parse(requestBody);

        String expectedIdName = context.read("$.idName");

        MockHttpServletResponse response = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getHeader("Location")).isNotNull();

        URI location = URI.create(response.getHeader("Location"));

        mockMvc.perform(get(location)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$").isMap())
                                .andExpect(jsonPath("$.idName").value(expectedIdName));


        assertThat(userRepository.findAll()).hasSize(4);
    }

    @Test
    public void shouldResponseNotFoundWhenUnregisterWithNotExistId() throws Exception {
        mockMvc.perform(delete("/users/10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldResponseNoContentWhenUnregisterSuccess() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(userRepository.findAll()).hasSize(2);
    }
}

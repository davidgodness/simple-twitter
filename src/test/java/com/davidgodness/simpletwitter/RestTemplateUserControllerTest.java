package com.davidgodness.simpletwitter;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateUserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql(value = "classpath:init/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldGetExpectedUsers() {
        ResponseEntity<String> results = restTemplate.getForEntity("/users", String.class);

        assertThat(results.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext actual = JsonPath.parse(results.getBody());

        Number id = actual.read("$.[0].id");
        String idName = actual.read("$.[0].idName");

        assertThat(id).isEqualTo(1);
        assertThat(idName).isEqualTo("zhangdawei");

        id = actual.read("$.[1].id");
        idName = actual.read("$.[1].idName");

        assertThat(id).isEqualTo(2);
        assertThat(idName).isEqualTo("davidchang");
    }
}

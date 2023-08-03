package com.davidgodness.simpletwitter;

import com.davidgodness.simpletwitter.user.User;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiTestWithTestRestTemplate {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void shouldGetExpectedUsers() {
        ResponseEntity<String> results = restTemplate.getForEntity("/users", String.class);

        assertThat(results.getStatusCode()).isEqualTo(HttpStatus.OK);

        LogFactory.getLog(UserApiTestWithTestRestTemplate.class).info(results.getBody());

        DocumentContext actual = JsonPath.parse(results.getBody());

        Number id = actual.read("$[0].id");
        String idName = actual.read("$[0].id_name");

        assertThat(id).isEqualTo(1);
        assertThat(idName).isEqualTo("daan");

    }
}

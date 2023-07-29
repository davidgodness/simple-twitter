package com.davidgodness.simpletwitter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private WebTestClient client;

    @Test
    public void testGetUsers() {
        client.get().uri("/users").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
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

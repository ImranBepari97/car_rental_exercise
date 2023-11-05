package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.ExerciseApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExerciseApplication.class)

public class BaseIntegrationTest {
    @LocalServerPort
    private int port;

    protected final RestTemplate restTemplate = new RestTemplate();


    protected final ObjectMapper objectMapper;

    private HttpHeaders headers = new HttpHeaders();

    public BaseIntegrationTest() {
        this.objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();


        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    public HttpEntity<String> createRequestFrom(Object object)  {
        try {
            return new HttpEntity<>(objectMapper.writeValueAsString(object), headers);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }

        return null;
    }
}

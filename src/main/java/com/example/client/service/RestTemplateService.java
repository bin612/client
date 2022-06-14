package com.example.client.service;

import com.example.client.dto.UserResponse;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    // http://localhost/api/server/hello
    // response
    public UserResponse hello() {
        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/hello")
            .queryParam("name", "bin")
            .queryParam("age", 90)
            .encode()
            .build()
            .toUri();

        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        // String result = restTemplate.getForObject(uri, String.class);

        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        // getBody로 return을 해줘야한다.
        return result.getBody();
    }
}

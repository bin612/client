package com.example.client.service;

import com.example.client.dto.UserRequest;
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

    public UserResponse post(){
        // http://localhost:9090/api/server/user/{userAge}/name/{userName}

        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/user/{userAge}/name/{userName}")
            .encode()
            .build()
            .expand(100, "bin") //expand 순서대로 매칭 ,(콤마)로 구분
            .toUri();
        System.out.println(uri);

        // http body -> object -> object mapper -> json -> rest template -> http body json
        // object mapper 가 자연스럽게 json 으로 변경시켜줌
        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(29);

        RestTemplate restTemplate = new RestTemplate();
        // 응답을 무엇으로 받을지만 정해주면 된다. (아래와 같이 응답을 앤티티로 받을지를 지정해주면 된다.)
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponse.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }
}

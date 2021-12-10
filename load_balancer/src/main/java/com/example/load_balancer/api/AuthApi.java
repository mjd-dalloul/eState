package com.example.load_balancer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
@RibbonClient(name = "auth-api")
public class AuthApi {


    @Autowired
    final RestTemplate template;

    @PostMapping("/login")
    public Object login(@RequestBody Object loginRequest, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://auth-api/api/public/login", HttpMethod.POST,
                new HttpEntity<>(loginRequest, httpHeaders), Object.class
        );
    }

    @PostMapping("/register")
    public Object register(@RequestBody Object request, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://auth-api/api/public/register", HttpMethod.POST,
                new HttpEntity<>(request, httpHeaders), Object.class
        );
    }

}

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
@RequestMapping(path = "api/constant")
@RequiredArgsConstructor
@RibbonClient(name = "constant-api")
public class ConstantApi {

    @Autowired
    RestTemplate template;

    @PutMapping("")
    public Object changeConstantValue(
            @RequestBody Object constantViewModel, @RequestHeader HttpHeaders httpHeaders
            ) {
        return template.exchange("http://constant-api/api/constant", HttpMethod.PUT,
                new HttpEntity<>(constantViewModel, httpHeaders), Object.class
        );

    }

    @GetMapping("")
    public Object fetchConstants(@RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://constant-api/api/constant",
                HttpMethod.GET, new HttpEntity<>(httpHeaders),
                Object.class
        );
    }
}

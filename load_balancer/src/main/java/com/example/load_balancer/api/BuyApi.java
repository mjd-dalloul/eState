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
@RequestMapping(path = "api/buy")
@RequiredArgsConstructor
@RibbonClient(name = "buy-api")
public class BuyApi {

    @Autowired
    private RestTemplate template;

    @PutMapping("")
    public Object buyProperty(@RequestParam Long id, @RequestBody Object buyerInfo, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/buy?id=" + id, HttpMethod.PUT,
                new HttpEntity<>(buyerInfo, httpHeaders), Object.class);

    }


    @GetMapping("")
    public Object fetchFormDetails(@RequestParam Long id, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/buy?id=",
                HttpMethod.GET, new HttpEntity<>(httpHeaders),
                Object.class
        );
    }

}

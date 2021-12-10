package com.example.load_balancer.api;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/property")
@RequiredArgsConstructor
@RibbonClient(name = "property-api")
public class PropertyApi {

    @Autowired
    RestTemplate template;

    @GetMapping("")
    public Object getPropertyHandler(@RequestParam @Nullable Long id, @RequestHeader HttpHeaders httpHeaders) {
        if (id == null) {
            return getAllProperty(httpHeaders);
        } else {
            return getPropertyById(id, httpHeaders);
        }
    }

    private Object getAllProperty(HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/property",
                HttpMethod.GET, new HttpEntity<>(httpHeaders),
                Object.class
        );

    }

    private Object getPropertyById(Long id, HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/property?id=" + id,
                HttpMethod.GET, new HttpEntity<>(httpHeaders),
                Object.class
        );
    }

    @DeleteMapping("")
    public Object deleteProperty(@RequestParam Long id, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/property?id=" + id, HttpMethod.DELETE,
                new HttpEntity<>(httpHeaders), Object.class
        );
    }

    @PutMapping("")
    public Object updateProperty(
            @RequestParam Long id, @RequestBody @Valid Object propertyViewModel,
            @RequestHeader HttpHeaders httpHeaders
    ) {
        return template.exchange("http://property-api/api/property?id=" + id, HttpMethod.PUT,
                new HttpEntity<>(propertyViewModel, httpHeaders), Object.class);
    }

    @PostMapping("")
    public Object createProperty(
            @RequestBody @Valid Object newProperty, @RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/property", HttpMethod.POST,
                new HttpEntity<>(newProperty, httpHeaders),
                Object.class
        );

    }

    @GetMapping("/availableProperty")
    public Object getAvailableProperty(@RequestHeader HttpHeaders httpHeaders) {
        return template.exchange("http://property-api/api/property/availableProperty",
                HttpMethod.GET, new HttpEntity<>(httpHeaders),
                Object.class
        );
    }
}

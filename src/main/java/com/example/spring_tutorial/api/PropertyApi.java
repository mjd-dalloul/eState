package com.example.spring_tutorial.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/property")
@RequiredArgsConstructor
public class PropertyApi {

}

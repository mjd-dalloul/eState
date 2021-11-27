package com.example.spring_tutorial.api;

import com.example.spring_tutorial.domain.dto.BaseResponse;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.service.BuyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/buy")
@RequiredArgsConstructor
public class BuyApi {
    private final BuyingService buyingService;


}

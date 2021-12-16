package com.example.spring_tutorial.api;

import com.example.spring_tutorial.domain.dto.BaseResponse;
import com.example.spring_tutorial.domain.dto.Shift;
import com.example.spring_tutorial.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(path = "api/activeMessageQueueApi")
@RequiredArgsConstructor
@Profile("!receiver")
public class ActiveMessageQueueApi {
    @Autowired
    private MessageSenderService service;

    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> sendMessage(@RequestBody @NotNull Shift shift) throws IOException, TimeoutException {
        service.sendMessage(shift);
        return ResponseEntity.ok().body(
                BaseResponse.<String>builder()
                        .success(true)
                        .data("Message sent Successfully")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}

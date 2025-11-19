package com.rodem.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Operation(
            summary = "루트 경로 테스트",
            description = "애플리케이션 동작을 확인하는 테스트용 API"
    )
    @GetMapping("/")
    public String test() {
        return "hello";
    }
}

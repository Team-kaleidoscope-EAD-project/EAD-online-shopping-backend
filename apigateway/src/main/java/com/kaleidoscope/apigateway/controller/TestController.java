package com.kaleidoscope.apigateway.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/apigateway")
public class TestController {

    @GetMapping("/public")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("test", "test");
        return response;
    }

}

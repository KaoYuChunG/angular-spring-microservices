package com.kao.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/auth/test-headers")
    public Map<String, String> testHeaders(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Roles", required = false) String userRoles) {
        
        return Map.of(
            "X-User-Id", userId != null ? userId : "null",
            "X-User-Roles", userRoles != null ? userRoles : "null"
        );
    }
}

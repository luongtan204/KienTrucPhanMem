package com.example.jwt.web;

import com.example.jwt.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token/demo")
    public Map<String, Object> demoToken(@RequestParam(name = "scope", defaultValue = "read") String scopeCsv) throws Exception {
        List<String> scopes = Arrays.stream(scopeCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        String token = tokenService.mintDemoToken(scopes);
        return Map.of("token", token, "scope", scopes, "expiresInSeconds", 3600);
    }
}

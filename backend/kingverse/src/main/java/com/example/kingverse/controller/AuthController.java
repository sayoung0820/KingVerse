package com.example.kingverse.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return Map.of("authenticated", false);
        }
        var roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Map.of(
                "authenticated", true,
                "username", auth.getName(),
                "roles", roles
        );
    }
}




package com.example.kingverse.controller;
import com.example.kingverse.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class SecurityDebugController {
    private final UserAccountRepository users;
    private final PasswordEncoder encoder;

    public SecurityDebugController(UserAccountRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @GetMapping("/pwreset/{username}")
    public Object resetGet(@PathVariable String username, @RequestParam String pw) {
        return reset(username, pw); // delegate to your POST reset()
    }

    @GetMapping("/pwcheck/{username}")
    public Object pwcheck(@PathVariable String username) {
        var ua = users.findByUsername(username).orElse(null);
        if (ua == null) return Map.of("found", false);
        var hash = ua.getPasswordHash();
        return Map.of(
                "found", true,
                "enabled", ua.getEnabled(),
                "hashLen", hash == null ? null : hash.length(),
                "hashPrefix", hash == null || hash.length()<4 ? hash : hash.substring(0,4),
                "matches_password",  encoder.matches("password", hash),
                "matches_admin123", encoder.matches("admin123", hash)
        );
    }

    // Add to SecurityDebugController

    @PostMapping("/pwreset/{username}")
    public Object reset(@PathVariable String username, @RequestParam String pw) {
        var ua = users.findByUsername(username).orElseThrow();
        var newHash = encoder.encode(pw);
        ua.setPasswordHash(newHash);
        ua.setEnabled(Boolean.TRUE);
        users.save(ua);
        return java.util.Map.of(
                "updated", true,
                "username", ua.getUsername(),
                "hashPrefix", newHash.substring(0, 4),
                "hashLen", newHash.length()
        );
    }

    @GetMapping("/pwpeek/{raw}")
    public Object peek(@PathVariable String raw) {
        var h = encoder.encode(raw);
        return java.util.Map.of(
                "raw", raw,
                "sampleHashPrefix", h.substring(0, 4),
                "sampleHashLen", h.length()
        );
    }

}

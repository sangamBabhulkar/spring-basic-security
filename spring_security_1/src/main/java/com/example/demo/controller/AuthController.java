package com.example.demo.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecretKey key = Keys.hmacShaKeyFor("YourSuperSecretKeyThatIsAtLeast256BitsLong!".getBytes(StandardCharsets.UTF_8));

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Step 2 & 3: Let AuthenticationManager check database credentials
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Generate JWT Token if login succeeds
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day validity
                .signWith(key)
                .compact();
    }
}
package com.dev.backend.rest.api;

import com.dev.backend.jwt.JwtAuthenticationResponse;
import com.dev.backend.jwt.Token;
import com.dev.backend.rest.dto.LogInRequest;
import com.dev.backend.rest.dto.SignUpRequest;
import com.dev.backend.security.CustomUserDetails;
import com.dev.backend.service.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final Authentication authentication;
    @PostMapping("/signup")
    public ResponseEntity<CustomUserDetails> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authentication.signUp(signUpRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(authentication.logIn(logInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> logIn(@RequestBody Token token) {
        return ResponseEntity.ok(authentication.refreshToken(token));
    }
    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> profile(HttpServletRequest request){
        return ResponseEntity.ok(authentication.getProfile(request));
    }
}

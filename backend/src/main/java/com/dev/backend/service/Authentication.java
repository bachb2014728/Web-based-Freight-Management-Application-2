package com.dev.backend.service;

import com.dev.backend.jwt.JwtAuthenticationResponse;
import com.dev.backend.jwt.Token;
import com.dev.backend.rest.dto.LogInRequest;
import com.dev.backend.rest.dto.SignUpRequest;
import com.dev.backend.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface Authentication {
    CustomUserDetails signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse logIn(LogInRequest logInRequest);

    JwtAuthenticationResponse refreshToken(Token token);

    CustomUserDetails getProfile(HttpServletRequest request);
}
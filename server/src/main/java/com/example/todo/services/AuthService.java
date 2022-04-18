package com.example.todo.services;

import com.example.todo.entities.User;
import com.example.todo.dto.AuthResponseDto;
import com.example.todo.dto.RefreshTokenRequestDto;
import com.example.todo.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthService(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponseDto performRefreshTokenPair(RefreshTokenRequestDto refreshTokenRequest) {
        var email = jwtProvider.getLoginFromToken(refreshTokenRequest.getRefreshToken());
        var user = userService.loadUserByEmail(email);
        return generateAuthResponseForUser(user);
    }

    private AuthResponseDto generateAuthResponseForUser(User user) {
        return AuthResponseDto.of(
                jwtProvider.generateAccessToken(user),
                jwtProvider.generateRefreshToken(user)
        );
    }
}

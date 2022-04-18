package com.example.todo.controllers;

import com.example.todo.dto.UserDto;
import com.example.todo.entities.User;
import com.example.todo.services.AuthService;
import com.example.todo.dto.AuthResponseDto;
import com.example.todo.dto.RefreshTokenRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("refresh")
    public AuthResponseDto refreshTokenPair(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        return authService.performRefreshTokenPair(refreshTokenRequest);
    }

    @GetMapping("/me")
    public UserDto whoAmI(@AuthenticationPrincipal User user) {
        return UserDto.fromUser(user);
    }

}

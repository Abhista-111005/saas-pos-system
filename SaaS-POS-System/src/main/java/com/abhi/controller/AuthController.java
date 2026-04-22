package com.abhi.controller;

import com.abhi.exceptions.UserException;
import com.abhi.payload.dto.UserDto;
import com.abhi.payload.response.AuthResponse;
import com.abhi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDto userDto)
            throws UserException {
        return ResponseEntity.ok(
                authService.signup(userDto)
        );
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDto userDto)
            throws UserException {
        return ResponseEntity.ok(
                authService.login(userDto)
        );
    }


}

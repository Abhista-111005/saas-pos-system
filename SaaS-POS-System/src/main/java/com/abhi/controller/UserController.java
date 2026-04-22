package com.abhi.controller;

import com.abhi.exceptions.UserException;
import com.abhi.mapper.UserMapper;
import com.abhi.model.User;
import com.abhi.payload.dto.UserDto;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, Exception {
        User user = userService.getUserById(id);

        if(user == null) {
            throw new UserException("User not found");
        }

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

}

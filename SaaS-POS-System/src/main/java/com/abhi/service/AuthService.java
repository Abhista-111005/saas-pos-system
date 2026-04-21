package com.abhi.service;

import com.abhi.exceptions.UserException;
import com.abhi.payload.dto.UserDto;
import com.abhi.payload.response.AuthResponse;

public interface AuthService  {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;



}

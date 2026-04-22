package com.abhi.service.impl;

import com.abhi.configuration.Jwtprovider;
import com.abhi.exceptions.UserException;
import com.abhi.model.User;
import com.abhi.repository.UserRepository;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final Jwtprovider jwtprovider;

    @Override
    public User getUserFromJwtToken(String token) throws UserException {

        String email = jwtprovider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("Invalid tokens");
        }

        return user;
    }

    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email);
            if(user == null) {
                throw new UserException("User not found");
            }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws UserException, Exception {
       return userRepository.findById(id).orElseThrow(
                () -> new Exception("User not found")
       );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

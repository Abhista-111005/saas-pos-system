package com.abhi.service.impl;

import com.abhi.configuration.Jwtprovider;
import com.abhi.domain.UserRole;
import com.abhi.exceptions.UserException;
import com.abhi.mapper.UserMapper;
import com.abhi.model.User;
import com.abhi.payload.dto.UserDto;
import com.abhi.payload.response.AuthResponse;
import com.abhi.repository.UserRepository;
import com.abhi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwtprovider jwtprovider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new UserException("email Id already registered !");
        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role Admin is not Allowed  !");
        }
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        User  savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDto.getEmail(),
                userDto.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtprovider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User registered successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();

        String jwt = jwtprovider.generateToken(authentication);

        User user = userRepository.findByEmail(email);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("LoggedIn Successfully");
        authResponse.setUser(UserMapper.toDTO(user));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);


        return null;
    }

    private Authentication authenticate(String email, String password) throws UserException {

        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails!=null) {
            throw new UserException("email id doesnt exsist "+ email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new UserException("password doesnt match");
        }


        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());


    }

}

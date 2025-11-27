package com.pasupathi.TasksApp.service.impl;


import com.pasupathi.TasksApp.dto.Response;
import com.pasupathi.TasksApp.dto.UserRequest;
import com.pasupathi.TasksApp.entity.User;
import com.pasupathi.TasksApp.enums.Role;
import com.pasupathi.TasksApp.exceptions.BadRequestException;
import com.pasupathi.TasksApp.exceptions.NotFoundException;
import com.pasupathi.TasksApp.repo.UserRepository;
import com.pasupathi.TasksApp.security.JwtUtils;
import com.pasupathi.TasksApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public Response<?> signUp(UserRequest userRequest) {
        log.info("Inside signUp()");
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if (existingUser.isPresent()){
            throw new BadRequestException("Username already taken");
        }

        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        //save the user
        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("user registered successfully")
                .build();

    }

    @Override
    public Response<?> login(UserRequest userRequest) {

        log.info("Inside signUp()");

        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid Password");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successful")
                .data(token)
                .build();

    }

    @Override
    public User getCurrentLoggedInUser() {

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }



}

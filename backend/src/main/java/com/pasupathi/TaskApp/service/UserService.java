package com.pasupathi.TasksApp.service;

import com.pasupathi.TasksApp.dto.Response;
import com.pasupathi.TasksApp.dto.UserRequest;
import com.pasupathi.TasksApp.entity.User;

public interface UserService {

    Response<?> signUp(UserRequest userRequest);
    Response<?> login(UserRequest userRequest);
    User getCurrentLoggedInUser();
}

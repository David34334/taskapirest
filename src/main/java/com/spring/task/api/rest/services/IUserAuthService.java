package com.spring.task.api.rest.services;

import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.entity.User;

public interface IUserAuthService {

    ResponseDTO<User> logInUserRequest(AuthUserDTO authUserDTO);

}
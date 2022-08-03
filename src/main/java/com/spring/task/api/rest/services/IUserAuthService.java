package com.spring.task.api.rest.services;

import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserAuthDTO;
import org.springframework.stereotype.Service;

@Service
public interface IUserAuthService {

    ResponseDTO<UserAuthDTO> logInUserRequest(AuthUserDTO authUserDTO);

}
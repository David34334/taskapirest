package com.spring.task.api.rest.controllers;

import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.services.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private IUserAuthService userAuthService;

    @PostMapping
    public ResponseDTO<User> authUserRequest(@RequestBody AuthUserDTO authUserDTO) {
        return userAuthService.logInUserRequest( authUserDTO );
    }

}
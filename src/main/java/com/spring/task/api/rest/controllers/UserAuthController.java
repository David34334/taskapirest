package com.spring.task.api.rest.controllers;

import com.spring.task.api.rest.exceptions.Unauthorized;
import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserAuthDTO;
import com.spring.task.api.rest.security.jwt.AuthValidator;
import com.spring.task.api.rest.services.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private IUserAuthService userAuthService;

    @Autowired
    private AuthValidator authValidator;

    @PostMapping
    public ResponseDTO<UserAuthDTO> authUserRequest(@RequestBody AuthUserDTO authUserDTO, @RequestParam("grant_type") String grandType) throws Unauthorized {
        authValidator.validateGrantType( authUserDTO, grandType );
        return userAuthService.logInUserRequest( authUserDTO );
    }

}
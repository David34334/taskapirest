package com.spring.task.api.rest.services.impl;

import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.services.IUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.spring.task.api.rest.constants.Constanst.SERVER_ERROR_MESSAGE;

@Service
@Slf4j
public class UserAuthServiceImpl implements IUserAuthService {

    @Autowired
    private IUserRepository userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO<User> logInUserRequest(AuthUserDTO authUserDTO) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            User user = userDAO.findByEmail(authUserDTO.getEmail());
            if ( user != null ) {
                if ( passwordEncoder.matches(authUserDTO.getPassword(), user.getPassword()) ) {
                    user.setPassword("");
                    responseDTO.setStatus(HttpStatus.OK.value());
                    responseDTO.setMessage("User LogIn Successful");
                    responseDTO.setData(user);
                }
            } else {
                responseDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
                responseDTO.setMessage(HttpStatus.UNAUTHORIZED + ". Email / Password are invalid. Try again.");
            }
        } catch ( Exception e ) {
            log.info("Error at authUserRequest :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

}
package com.spring.task.api.rest.services.impl;

import com.spring.task.api.rest.models.dto.AuthUserDTO;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserAuthDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.security.jwt.AuthValidator;
import com.spring.task.api.rest.security.jwt.JwtIO;
import com.spring.task.api.rest.services.IUserAuthService;
import com.spring.task.api.rest.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    AuthValidator authValidator;

    @Autowired
    private JwtIO jwtIO;

    @Value("${jwt.token.expires-in}")
    private int EXPIRES_IN;

    @Override
    public ResponseDTO<UserAuthDTO> logInUserRequest(AuthUserDTO authUserDTO) {
        ResponseDTO<UserAuthDTO> responseDTO = new ResponseDTO<>();
        try {
            User user = userDAO.findByEmail(authUserDTO.getEmail());
            if ( user != null ) {
                if ( passwordEncoder.matches(authUserDTO.getPassword(), user.getPassword()) ) {
                    user.setPassword("");
                    UserAuthDTO userAuthDTO = UserAuthDTO.builder()
                            .tokenType("Bearer")
                            .accessToken(jwtIO.generateNewToken(user))
                            .issuedAt(DateUtil.simpleDateFormat())
                            .user(user.getEmail() + " " + user.getUsername())
                            .userId(user.getId())
                            .expiresIn(EXPIRES_IN)
                            .build();
                    responseDTO.setStatus(HttpStatus.OK.value());
                    responseDTO.setData(userAuthDTO);
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
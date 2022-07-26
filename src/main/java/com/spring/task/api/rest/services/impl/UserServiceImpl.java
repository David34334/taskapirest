package com.spring.task.api.rest.services.impl;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spring.task.api.rest.constants.Constanst.SERVER_ERROR_MESSAGE;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO<User> getAllUsers() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            List<User> userList = (List<User>) userDAO.findAll();
            if ( !userList.isEmpty() ) {
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.toString());
                responseDTO.setDataList(userList);
            } else {
                responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
                responseDTO.setMessage("No User records to show");
            }
        } catch ( Exception e ) {
            log.info("Error at getAllUsers :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<User> getUserByID(Long id) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            Optional<User> user = userDAO.findById( id );
            if ( user.isPresent() ) {
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.toString());
                responseDTO.setData(user.get());
            } else {
                responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
                responseDTO.setMessage("No User records to show");
            }
        } catch ( Exception e ) {
            log.info("Error at getUserByID :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<User> createUser(UserDTO user) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            User u = userDAO.findByEmail( user.getEmail() );
            if ( u == null ) {
                User userToCreate = new User();
                userToCreate.setEmail( user.getEmail() );
                userToCreate.setUsername( user.getUsername() );
                userToCreate.setPassword( passwordEncoder.encode( user.getPassword() ) );
                userToCreate.setCreatedAt( user.getCreatedAt() );

                userToCreate = userDAO.save(userToCreate);

                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.toString());
                responseDTO.setData(userToCreate);
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("User with email " + user.getEmail() + " already exists.");
            }
        } catch ( Exception e ) {
            log.info("Error at createUser :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<User> updateUser(UserDTO user) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            Optional<User> u = userDAO.findById( user.getId() );
            if ( u.isPresent() ) {
                User userToUpdate = u.get();
                userToUpdate.setUsername( (user.getUsername() != null) ? user.getUsername() : userToUpdate.getUsername() );
                userToUpdate.setEmail( (user.getEmail() != null) ? user.getEmail() : userToUpdate.getEmail() );
                userToUpdate = userDAO.save(userToUpdate);
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage("User updated with success");
                responseDTO.setData(userToUpdate);
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("User with id " + user.getId() + " doesn't exists");
            }
        } catch ( Exception e ) {
            log.info("Error at updateUserByID :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<User> deleteUserByID(Long id) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        try {
            Optional<User> user = userDAO.findById( id );
            if ( user.isPresent() ) {
                userDAO.deleteById( id );
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK + " :: User " + user.get().getUsername() + " deleted.");
                responseDTO.setData(user.get());
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("User with id " + id + " doesn't exists");
            }
        } catch ( Exception e ) {
            log.info("Error at deleteUserByID :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }
}

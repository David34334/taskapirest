package com.spring.task.api.rest.services;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserDTO;
import com.spring.task.api.rest.models.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    ResponseDTO<User> getAllUsers();

    ResponseDTO<User> getUserByID(Long id);

    ResponseDTO<User> createUser(UserDTO user);

    ResponseDTO<User> updateUser(UserDTO user);

    ResponseDTO<User> deleteUserByID(Long id);

}

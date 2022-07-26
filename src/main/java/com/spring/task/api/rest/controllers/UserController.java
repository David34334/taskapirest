package com.spring.task.api.rest.controllers;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.UserDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseDTO<User> getUserRequest(@RequestParam( value = "id", required = false ) Long id) {
        if ( id != null ) return userService.getUserByID( id );
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseDTO<User> createUserRequest(@RequestBody UserDTO user) {
        return userService.createUser( user );
    }

    @PutMapping
    public ResponseDTO<User> updateUserRequest(@RequestBody UserDTO user) {
        return userService.updateUser( user );
    }

    @DeleteMapping("/{idUser}")
    public ResponseDTO<User> deleteUserRequest(@PathVariable( value = "idUser" ) Long id) {
        return userService.deleteUserByID( id );
    }

}
package com.spring.task.api.rest.services;

import com.spring.task.api.rest.builder.UserBuilder;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static com.spring.task.api.rest.constants.Constanst.SERVER_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    /** User Builder */
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UserBuilder.getUserTest();
    }

    @Test
    void getAllUsersTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findAll() ).thenReturn(Collections.singletonList(user));
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage(HttpStatus.OK.toString());
        responseDTO.setDataList(Collections.singletonList(user));
        assertNotNull(userService.getAllUsers());
        assertEquals(responseDTO, userService.getAllUsers());
    }

    @Test
    void getAllUsersEmptyTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findAll() ).thenReturn( new ArrayList<>() );
        responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
        responseDTO.setMessage("No User records to show");
        assertEquals(responseDTO, userService.getAllUsers() );
    }

    @Test
    void getAllUsersServerError() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findAll() ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, userService.getAllUsers());
    }

    @Test
    void getUserByIdTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(user));
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage(HttpStatus.OK.toString());
        responseDTO.setData(user);
        assertEquals(responseDTO, userService.getUserByID(user.getId()));
    }

    @Test
    void getUserByIdNotValidTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(user));
        responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
        responseDTO.setMessage("No User records to show");
        assertEquals(responseDTO, userService.getUserByID(null));
    }

    @Test
    void getUserServerError() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, userService.getUserByID( anyLong() ));
    }

    @Test
    void createUserSuccessTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findByEmail(anyString()) ).thenReturn( null );
        when( userRepository.save(any(User.class)) ).thenReturn( user );
        when( passwordEncoder.encode(anyString()) ).thenReturn( new BCryptPasswordEncoder().encode( UserBuilder.createUserDTO().getPassword() ) );
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage(HttpStatus.OK.toString());
        responseDTO.setData(user);
        assertEquals(responseDTO, userService.createUser(UserBuilder.createUserDTO()));
    }

    @Test
    void createUserWithExistsEmailTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findByEmail(anyString()) ).thenReturn( user );
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDTO.setMessage("User with email " + UserBuilder.createUserDTO().getEmail() + " already exists.");
        assertEquals(responseDTO, userService.createUser(UserBuilder.createUserDTO()));
    }

    @Test
    void createUserServerError() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findByEmail(anyString()) ).thenReturn( null );
        when( userRepository.save(any(User.class)) ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, userService.createUser(null));
    }

    @Test
    void updateUserTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(user));
        when( userRepository.save(any(User.class)) ).thenReturn(user);
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage("User updated with success");
        responseDTO.setData(user);
        assertEquals(responseDTO, userService.updateUser(UserBuilder.createUserDTO()));
    }

    @Test
    void updateUserDoesnExistsTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn( Optional.empty() );
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDTO.setMessage("User with id " + UserBuilder.createUserDTO().getId() + " doesn't exists");
        assertEquals(responseDTO, userService.updateUser(UserBuilder.createUserDTO()));
    }

    @Test
    void updateUserServerError() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, userService.updateUser(UserBuilder.createUserDTO()));
    }

    @Test
    void deleteUserTest() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(user));
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage(HttpStatus.OK + " :: User " + user.getUsername() + " deleted.");
        responseDTO.setData(user);
        assertEquals(responseDTO, userService.deleteUserByID(anyLong()));
    }

    @Test
    void deleteUserWithWrongId() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(anyLong()) ).thenReturn( Optional.empty() );
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDTO.setMessage("User with id " + 0 + " doesn't exists");
        assertEquals(responseDTO, userService.deleteUserByID(anyLong()));
    }

    @Test
    void deleteUserServerError() {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        when( userRepository.findById(null) ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, userService.deleteUserByID( null ));
    }

}
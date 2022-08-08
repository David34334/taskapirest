package com.spring.task.api.rest.builder;

import com.spring.task.api.rest.models.dto.UserDTO;
import com.spring.task.api.rest.models.entity.User;

public class UserBuilder {

    public static User getUserTest() {
        User user = new User();
        user.setUsername("Test Username");
        user.setEmail("test@gmail.com");
        user.setPassword("Testpassword123");
        user.setCreatedAt("07/08/2022 - 12:37");
        user.setId(10001L);
        return user;
    }

    public static UserDTO createUserDTO() {
        return UserDTO.builder()
                .id(10000L)
                .password("TestCreateUser")
                .username("TestUsername")
                .email("testCreate@gmail.com")
                .createdAt("07/08/2022 - 17:00")
                .build();
    }
}
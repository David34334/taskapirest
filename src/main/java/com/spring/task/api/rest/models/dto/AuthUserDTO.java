package com.spring.task.api.rest.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.spring.task.api.rest.constants.Constanst.EMAIL_REQUIRED;
import static com.spring.task.api.rest.constants.Constanst.PASSWORD_REQUIRED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserDTO {

    @NotNull( message = EMAIL_REQUIRED )
    @NotEmpty( message = EMAIL_REQUIRED )
    @NotBlank( message = EMAIL_REQUIRED )
    private String email;

    @NotNull( message = PASSWORD_REQUIRED )
    @NotEmpty( message = PASSWORD_REQUIRED )
    @NotBlank( message = PASSWORD_REQUIRED )
    private String password;

}
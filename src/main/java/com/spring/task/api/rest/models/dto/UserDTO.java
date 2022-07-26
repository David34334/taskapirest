package com.spring.task.api.rest.models.dto;

import com.spring.task.api.rest.utils.DateUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.Date;

import static com.spring.task.api.rest.constants.Constanst.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    @Autowired
    private static DateUtil dateUtil = new DateUtil();

    private Long id;

    @NotBlank( message = USERNAME_REQUIRED )
    @NotNull( message = USERNAME_REQUIRED )
    @NotEmpty( message = USERNAME_REQUIRED )
    private String username;

    @NotBlank( message = EMAIL_REQUIRED )
    @NotNull( message = EMAIL_REQUIRED )
    @NotEmpty( message = EMAIL_REQUIRED )
    private String email;

    @NotBlank( message = PASSWORD_REQUIRED )
    @NotNull( message = PASSWORD_REQUIRED )
    @NotEmpty( message = PASSWORD_REQUIRED )
    private String password;

    @Builder.Default
    private String createdAt = dateUtil.simpleDateFormat();

}

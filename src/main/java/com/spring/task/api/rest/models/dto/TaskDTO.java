package com.spring.task.api.rest.models.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.spring.task.api.rest.constants.Constanst.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    private Long id;
    @NotBlank( message = TASK_NAME_REQUIRED )
    @NotNull( message = TASK_NAME_REQUIRED )
    @NotEmpty( message = TASK_NAME_REQUIRED )
    private String name;
    @NotBlank( message = TASK_DESCRIPTION_REQUIRED )
    @NotNull( message = TASK_DESCRIPTION_REQUIRED )
    @NotEmpty( message = TASK_DESCRIPTION_REQUIRED )
    private String description;
    @NotNull( message = TASK_STATUS_REQUIRED )
    @NotBlank( message = TASK_STATUS_REQUIRED )
    @NotEmpty( message = TASK_STATUS_REQUIRED )
    private boolean state;
    @Builder.Default
    private Date updatedAt = new Date(System.currentTimeMillis());
    @NotBlank( message = ID_USER_REQUIRED )
    @NotNull( message = ID_USER_REQUIRED )
    @NotEmpty( message = ID_USER_REQUIRED )
    private Long idUser;

}

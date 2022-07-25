package com.spring.task.api.rest.models.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    private Long id;
    @NotBlank( message = "Name task is required.")
    @NotNull( message = "Name task is required.")
    @NotEmpty( message = "Name task is required.")
    private String name;
    @NotBlank( message = "Description task is required.")
    @NotNull( message = "Description task is required.")
    @NotEmpty( message = "Description task is required.")
    private String description;
    @NotNull( message = "Status task is required.")
    private boolean state;
    @Builder.Default
    private Date updatedAt = new Date(System.currentTimeMillis());
    @NotBlank( message = "ID user is required.")
    @NotNull( message = "ID user is required.")
    @NotEmpty( message = "ID user is required.")
    private Long idUser;

}

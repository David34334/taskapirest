package com.spring.task.api.rest.controllers;

import com.spring.task.api.rest.exceptions.Unauthorized;
import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.security.jwt.AuthValidator;
import com.spring.task.api.rest.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    AuthValidator authValidator;

    @Autowired
    private ITaskService taskService;

    @GetMapping
    public ResponseDTO<Task> getTaskRequest(@RequestParam( value = "id", required = false ) Long id, @RequestHeader("Authorization") String token) throws Unauthorized {
        authValidator.validarToken( token );
        if ( id != null ) return taskService.getTaskByID( id );
        return taskService.getAllTask();
    }

    @GetMapping("/{idUser}")
    public ResponseDTO<Task> getAllTaskByUser(@PathVariable("idUser") Long id, @RequestHeader("Authorization") String token) throws Unauthorized {
        authValidator.validarToken( token );
        return taskService.getAllTaskByUser( id );
    }

    @PostMapping
    public ResponseDTO<Task> createTaskRequest(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token) throws Unauthorized {
        authValidator.validarToken( token );
        return taskService.createTask(taskDTO);
    }

    @PutMapping
    public ResponseDTO<Task> updatedTaskRequest(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token) throws Unauthorized {
        authValidator.validarToken( token );
        return taskService.updateTask(taskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Task> deleteTaskRequest(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws Unauthorized {
        authValidator.validarToken( token );
        return taskService.deleteTaskByID(id);
    }

}

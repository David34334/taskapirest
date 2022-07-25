package com.spring.task.api.rest.controllers;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping
    public ResponseDTO<Task> getTaskRequest(@RequestParam( value = "id", required = false ) Long id) {
        if ( id != null ) return taskService.getTaskByID( id );
        return taskService.getAllTask();
    }

    @GetMapping("/{idUser}")
    public ResponseDTO<Task> getAllTaskByUser(@PathVariable("idUser") Long id) {
        return taskService.getAllTaskByUser( id );
    }

    @PostMapping
    public ResponseDTO<Task> createTaskRequest(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping
    public ResponseDTO<Task> updatedTaskRequest(@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Task> deleteTaskRequest(@PathVariable("id") Long id) {
        return taskService.deleteTaskByID(id);
    }

}

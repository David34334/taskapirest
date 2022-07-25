package com.spring.task.api.rest.services;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import org.springframework.stereotype.Service;

@Service
public interface ITaskService {

    ResponseDTO<Task> getAllTask();

    ResponseDTO<Task> getTaskByID(Long id);

    ResponseDTO<Task> getAllTaskByUser(Long idUser);

    ResponseDTO<Task> createTask(TaskDTO taskDTO);

    ResponseDTO<Task> updateTask(TaskDTO taskDTO);

    ResponseDTO<Task> deleteTaskByID(Long id);

}
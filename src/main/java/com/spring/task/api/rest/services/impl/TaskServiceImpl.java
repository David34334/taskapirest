package com.spring.task.api.rest.services.impl;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.entity.UsersTasks;
import com.spring.task.api.rest.models.repositories.ITaskRepository;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.models.repositories.IUsersTasksRepository;
import com.spring.task.api.rest.services.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.spring.task.api.rest.constants.Constanst.SERVER_ERROR_MESSAGE;

@Service
@Slf4j
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskRepository taskDAO;

    @Autowired
    private IUserRepository userDAO;

    @Autowired
    private IUsersTasksRepository userTaskDAO;

    @Override
    public ResponseDTO<Task> getAllTask() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            List<Task> taskList = (List<Task>) taskDAO.findAll();
            if ( !taskList.isEmpty() ) {
                responseDTO.setMessage(HttpStatus.OK.toString());
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setDataList(taskList);
            } else {
                responseDTO.setMessage("No task records to show");
                responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
            }
        } catch ( Exception e ) {
            log.info("Error at getAllTask :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Task> getTaskByID(Long id) {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            Optional<Task> task = taskDAO.findById( id );
            if ( task.isPresent() ) {
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage(HttpStatus.OK.toString());
                responseDTO.setData(task.get());
            } else {
                responseDTO.setMessage("Task with ID " + id + " doesn't exists in DB.");
                responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
            }
        } catch ( Exception e ) {
            log.info("Error at getTaskByID :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Task> getAllTaskByUser(Long idUser) {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            Optional<User> user = userDAO.findById( idUser );
            if ( user.isPresent() ) {
                List<UsersTasks> userTask = userTaskDAO.findAllByUser(user.get());
                if ( !userTask.isEmpty() ) {
                    List<Task> taskList = new ArrayList<>();
                    userTask.parallelStream().forEach(item -> taskList.add(item.getTask()));
                    responseDTO.setStatus(HttpStatus.OK.value());
                    responseDTO.setMessage(HttpStatus.OK.toString());
                    responseDTO.setDataList(taskList);
                } else {
                    responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
                    responseDTO.setMessage("User doesn't create any task yet");
                }
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("User with ID " + idUser + " doesn't exists");
            }
        } catch ( Exception e ) {
            log.info("Error at getAllTaskByUser :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Task> createTask(TaskDTO taskDTO) {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            Optional<User> user = userDAO.findById(taskDTO.getIdUser());
            if ( user.isPresent() ) {
                Task task = new Task();
                task.setName(taskDTO.getName());
                task.setDescription(taskDTO.getDescription());
                task.setState(taskDTO.isState());
                task.setLastUpdatedAt(taskDTO.getUpdatedAt());
                task = taskDAO.save(task);

                saveUserTask(task, user.get());
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage("Task created with success");
                responseDTO.setData(task);
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("User associate with this task are invalid.");
            }
        } catch ( Exception e ) {
            log.info("Error at createTask :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Task> updateTask(TaskDTO taskDTO) {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            Optional<Task> task = taskDAO.findById(taskDTO.getId());
            if ( task.isPresent() ) {
                task.get().setDescription((taskDTO.getDescription() != null) ? taskDTO.getDescription() : task.get().getDescription());
                task.get().setName((taskDTO.getName() != null) ? taskDTO.getName() : task.get().getName());
                task.get().setState((taskDTO.isState()));
                task.get().setLastUpdatedAt(new Date(System.currentTimeMillis()));

                Task t = taskDAO.save(task.get());
                responseDTO.setStatus(HttpStatus.OK.value());
                responseDTO.setMessage("Task updated with sucsess");
                responseDTO.setData(t);
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage(HttpStatus.BAD_REQUEST + " ID task associate doesn't existis in DB");
            }
        } catch ( Exception e ) {
            log.info("Error at updatedTask :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Task> deleteTaskByID(Long id) {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        try {
            Optional<Task> task = taskDAO.findById( id );
            if ( task.isPresent() ) {
                UsersTasks usersTasks = userTaskDAO.findByTask(task.get());
                if ( usersTasks != null ) {
                    taskDAO.delete(task.get());
                    responseDTO.setStatus(HttpStatus.OK.value());
                    responseDTO.setMessage("Task deleted correctly");
                    responseDTO.setData(task.get());
                } else {
                    responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                    responseDTO.setMessage("Task with ID " + id + " doesn't exists");
                }
            } else {
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
                responseDTO.setMessage("Task with ID " + id + " doesn't exists");
            }
        } catch ( Exception e ) {
            log.info("Error at deteleTaskByID :: " + e);
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        }
        return responseDTO;
    }

    private void saveUserTask(Task task, User user) {
        UsersTasks usersTasks = new UsersTasks();
        usersTasks.setTask(task);
        usersTasks.setUser(user);
        userTaskDAO.save(usersTasks);
    }
}
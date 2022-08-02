package com.spring.task.api.rest.services;

import com.spring.task.api.rest.models.dto.ResponseDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.models.repositories.ITaskRepository;
import com.spring.task.api.rest.models.repositories.IUserRepository;
import com.spring.task.api.rest.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.*;

import static com.spring.task.api.rest.constants.Constanst.SERVER_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {

    @Mock
    ITaskRepository taskRepository;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setName("Testing Name");
        task.setDescription("Testing Description");
        task.setState(true);
        task.setCreatedAt(new Date(System.currentTimeMillis()));
        task.setLastUpdatedAt(new Date(System.currentTimeMillis()));
        task.setId(100000L);
    }

    @Test
    void getAllTaskMockito() {
        when( taskRepository.findAll() ).thenReturn(Collections.singletonList(task));
        assertNotNull(taskService.getAllTask());
    }

    @Test
    void getAllTaskNull() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        when( taskRepository.findAll() ).thenReturn(new ArrayList<>());
        responseDTO.setMessage("No task records to show");
        responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
        assertEquals(responseDTO, taskService.getAllTask());
    }

    @Test
    void getAllTaskNullException() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        when( taskRepository.findAll() ).thenReturn( null );
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, taskService.getAllTask());
    }

    @Test
    void getTaskByID() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        when( taskRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(task));
        responseDTO.setStatus(HttpStatus.OK.value());
        responseDTO.setMessage(HttpStatus.OK.toString());
        responseDTO.setData(task);
        assertEquals(responseDTO, taskService.getTaskByID(100000L));
    }

    @Test
    void getTaskByIdNull() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        when( taskRepository.findById(anyLong()) ).thenReturn(Optional.ofNullable(task));
        responseDTO.setStatus(HttpStatus.NO_CONTENT.value());
        responseDTO.setMessage("Task with ID null doesn't exists in DB.");
        assertEquals(responseDTO, taskService.getTaskByID(null));
    }

    @Test
    void getTaskByIdNullException() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();
        when( taskRepository.findById(anyLong()) ).thenReturn(null);
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMessage(SERVER_ERROR_MESSAGE);
        assertEquals(responseDTO, taskService.getTaskByID(anyLong()));
    }

    @Test
    void getAllTaskByUser() {
        ResponseDTO<Task> responseDTO = new ResponseDTO<>();

    }

}

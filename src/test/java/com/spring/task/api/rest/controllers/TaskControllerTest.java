package com.spring.task.api.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.models.repositories.ITaskRepository;
import com.spring.task.api.rest.util.Data;
import com.spring.task.api.rest.utils.ParseObjectsToJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TaskControllerTest {

    @Autowired
    private ITaskRepository taskDAO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParseObjectsToJson parseObjectsToJson;

    Data data = new Data();


    /**
     * @GetAllTaskTest return a JSON Object with Structure: (status, message, data, dataList). DataList has all Task.
     */
    @Test
    void getAllTaskTest() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.dataList").isArray());
        } catch ( Exception e ) {
            log.error("Error at getAllTaskTest: " + e);
        }
    }

    @Test
    void getTaskByID() {
        List<Task> taskList = (List<Task>) taskDAO.findAll();
        /* Get first record on DB and save his ID */
        Long idTask = taskList.get(0).getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/task")
                .param("id", idTask.toString() )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()) )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.data.id").value( idTask) );
        } catch ( Exception e ) {
            log.error("Error at GetTaskById: " + e);
        }
    }

    @Test
    void getTaskByIncorrectID() {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/task")
                .param("id", "3000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()) )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Task with ID 3000 doesn't exists in DB.") );
        } catch ( Exception e ) {
            log.error("Error at getTaskByIncorrectID: " + e);
        }
    }

    @Test
    void createTask() throws JsonProcessingException {
        TaskDTO task = data.getTask();
        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(task);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/task")
                .content(taskJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()) )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Task created with success") );
        } catch ( Exception e ) {
            log.error("Error at createUser: " + e);
        }
    }

    @Test
    void createInvalidTask() {
        TaskDTO taskDTO = data.invalidTask();
        String taskJson = parseObjectsToJson.convertObjectToJson( taskDTO );

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/task")
                .content(taskJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()) )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("User associate with this task are invalid.") );
        } catch ( Exception e ) {
            log.error("Error at createInvalidUser: " + e);
        }
    }

    @Test
    void updatedTask() {
        Optional<Task> task = taskDAO.findById(12L);
        if ( task.isPresent() ) {
            TaskDTO taskDTO = data.updateTask(task.get());
            taskDTO.setDescription("Description Updated at method");
            taskDTO.setName("Name updated at method");
            String taskJson = parseObjectsToJson.convertObjectToJson(taskDTO);

            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .put("/api/task")
                    .content(taskJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);
            try {
                mockMvc.perform( requestBuilder )
                        .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()) )
                        .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Task updated with sucsess") );
            } catch ( Exception e ) {
                log.error("Error at updatedTask: " + e);
            }
        } else {
            log.error("Error getting task at updated");
        }
    }

    @Test
    void deleteTask() {
        Optional<Task> task = taskDAO.findById(12L);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/task/12")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        try {
            mockMvc.perform( requestBuilder )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()) )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.message").value("Task deleted correctly") )
                    .andExpect( MockMvcResultMatchers.jsonPath("$.data.id").value(task.get().getId()) );
        } catch ( Exception e ) {
            log.error("Error at deleteTask: " + e);
        }
    }

}

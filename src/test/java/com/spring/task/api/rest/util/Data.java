package com.spring.task.api.rest.util;

import com.spring.task.api.rest.models.dto.TaskDTO;
import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Data {

    @Autowired
    private DateUtil dateUtil;

    public User getUser() {
        User user = new User();
        user.setUsername("DavidTest");
        user.setEmail("test@gmail.com");
        user.setPassword("12345678");
        user.setCreatedAt(dateUtil.simpleDateFormat());

        return user;
    }

    public TaskDTO getTask() {
        TaskDTO task = new TaskDTO();
        task.setName("Name Test");
        task.setDescription("Description Test");
        task.setState(false);
        task.setIdUser(9L);

        return task;
    }

    public TaskDTO updateTask(Task task) {
        TaskDTO t = new TaskDTO();
        t.setId( task.getId() );
        t.setName( task.getName() );
        t.setDescription( task.getDescription() );
        t.setState( task.isState() );
        t.setIdUser(4L);

        return t;
    }

    public TaskDTO invalidTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription("Test");
        taskDTO.setName("Test Name");
        taskDTO.setIdUser(3000L);
        taskDTO.setState(true);
        return taskDTO;
    }

}

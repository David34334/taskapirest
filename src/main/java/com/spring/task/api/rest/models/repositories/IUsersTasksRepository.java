package com.spring.task.api.rest.models.repositories;

import com.spring.task.api.rest.models.entity.Task;
import com.spring.task.api.rest.models.entity.User;
import com.spring.task.api.rest.models.entity.UsersTasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsersTasksRepository extends CrudRepository<UsersTasks, Long> {
    UsersTasks findByTask(Task task);
    List<UsersTasks> findAllByUser(User user);
}
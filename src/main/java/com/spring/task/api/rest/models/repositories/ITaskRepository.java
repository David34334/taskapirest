package com.spring.task.api.rest.models.repositories;

import com.spring.task.api.rest.models.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends CrudRepository<Task, Long> {}
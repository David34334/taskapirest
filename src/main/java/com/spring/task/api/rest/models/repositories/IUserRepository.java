package com.spring.task.api.rest.models.repositories;

import com.spring.task.api.rest.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
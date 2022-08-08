package com.spring.task.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Unauthorized extends Exception {

    public Unauthorized(String messsage) {
        super( messsage );
    }

}
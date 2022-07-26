package com.spring.task.api.rest.constants;

import org.springframework.http.HttpStatus;

public class Constanst {

    private Constanst() {
        throw new IllegalStateException("Constants Class");
    }

    public static final String SERVER_ERROR_MESSAGE = HttpStatus.INTERNAL_SERVER_ERROR + " error unexpected at server. Please contact with administrator";
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String TASK_NAME_REQUIRED = "Task name is required";
    public static final String TASK_DESCRIPTION_REQUIRED = "Task description is required";
    public static final String TASK_STATUS_REQUIRED = "Task status is required";
    public static final String ID_USER_REQUIRED = "ID User is required";

}

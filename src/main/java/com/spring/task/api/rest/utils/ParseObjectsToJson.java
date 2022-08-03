package com.spring.task.api.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ParseObjectsToJson {

    public static String convertObjectToJson(Object object) {
        String objectJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectJson = objectMapper.writeValueAsString( object );
        } catch ( Exception e ) {
            log.error("Error at convertObjectToJson: " + e);
        }
        return objectJson;
    }

}

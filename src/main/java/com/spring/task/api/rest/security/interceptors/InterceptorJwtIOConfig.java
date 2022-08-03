package com.spring.task.api.rest.security.interceptors;

import com.spring.task.api.rest.security.jwt.InterceptorJwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorJwtIOConfig implements WebMvcConfigurer {

    @Value("${jwt.security.enable}")
    private boolean securityEnabled;

    @Autowired
    private InterceptorJwtIO interceptorJwtIO;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if ( securityEnabled ) {
            registry.addInterceptor( interceptorJwtIO );
        }
    }
}
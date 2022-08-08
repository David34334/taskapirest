package com.spring.task.api.rest.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    @Value("${timezone.date}")
    private String TIMEZONE;

    public static String simpleDateFormat() {
        Date getDateNow = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        return simpleDateFormat.format(getDateNow);
    }

}
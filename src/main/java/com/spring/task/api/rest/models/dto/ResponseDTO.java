package com.spring.task.api.rest.models.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {

    @Builder.Default
    private int status = 200;
    @Builder.Default
    private String message = "";
    @Builder.Default
    private T data = (T) "";
    @Builder.Default
    private List<T> dataList = new ArrayList<>();

}

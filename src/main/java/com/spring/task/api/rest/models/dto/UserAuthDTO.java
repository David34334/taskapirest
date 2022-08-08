package com.spring.task.api.rest.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static com.spring.task.api.rest.constants.Constanst.*;

@Data
@Builder
public class UserAuthDTO {

    @JsonProperty( value = TOKEN_TYPE_JSON )
    private String tokenType;

    @JsonProperty( value = ACCESS_TOKEN_JSON )
    private String accessToken;

    @JsonProperty( value = EXPIRES_IN_JSON )
    private int expiresIn;

    @JsonProperty( value = ISSUED_AT_JSON )
    private String issuedAt;

    @JsonProperty( value = USER_JSON )
    private String user;

    @JsonProperty( value = USER_ID_JSON )
    private Long userId;

}

package com.treeleaf.task.response;

import lombok.Data;

/**
 * Created by User on 2/12/2022.
 */
@Data
public class AuthenticationResponse {

    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}

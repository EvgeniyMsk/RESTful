package edu.school21.restful.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

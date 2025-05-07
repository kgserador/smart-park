package com.smartpark.exam.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String email;
    private String username;
    private String password;
    private String name;
}

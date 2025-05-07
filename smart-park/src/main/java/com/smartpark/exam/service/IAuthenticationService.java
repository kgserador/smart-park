package com.smartpark.exam.service;

import com.smartpark.exam.dto.LoginUserDTO;
import com.smartpark.exam.dto.RegisterUserDTO;
import com.smartpark.exam.entity.User;

public interface IAuthenticationService {
    User signup(RegisterUserDTO input);
    User authenticate(LoginUserDTO input);
}

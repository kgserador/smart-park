package com.smartpark.exam.controller;

import com.smartpark.exam.dto.LoginResponseDTO;
import com.smartpark.exam.dto.LoginUserDTO;
import com.smartpark.exam.dto.RegisterUserDTO;
import com.smartpark.exam.entity.User;
import com.smartpark.exam.service.IAuthenticationService;
import com.smartpark.exam.service.IJWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smartpark/auth")
public class AuthenticationController {
    private final IJWTService jwtService;

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IJWTService jwtService, IAuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}

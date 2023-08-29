package com.hps.gestion.controller;

import com.hps.gestion.dto.Credentials;
import com.hps.gestion.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){this.loginService = loginService;}

    @PostMapping("/updateCredentials")
    public Credentials updateCredentials(@RequestBody Credentials credentials){
        return loginService.setCredentials(credentials);
    }

}

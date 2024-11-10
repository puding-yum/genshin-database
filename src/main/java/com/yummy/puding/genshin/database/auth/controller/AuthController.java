package com.yummy.puding.genshin.database.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

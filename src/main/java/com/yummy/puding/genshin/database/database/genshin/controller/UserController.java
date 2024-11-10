package com.yummy.puding.genshin.database.database.genshin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<Object> register(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

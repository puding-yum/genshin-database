package com.yummy.puding.genshin.database.database.genshin.controller;

import com.yummy.puding.genshin.database.database.genshin.client.req.RegisterReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/character")
public class CharacterController {
    private static final Logger log = LoggerFactory.getLogger(CharacterController.class);

    @PostMapping("/get")
    public ResponseEntity<Object> register(@RequestBody RegisterReq request) throws Exception {
        log.info("service character");
        throw new Exception("something");
//        return new ResponseEntity<>(HttpStatus.OK);
    }

}

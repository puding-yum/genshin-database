package com.yummy.puding.genshin.database.database.genshin.controller;

import com.yummy.puding.genshin.database.database.genshin.model.client.req.AddElementReq;
import com.yummy.puding.genshin.database.database.genshin.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/element")
public class ElementController {
    @Autowired
    private ElementService elementService;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        return elementService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AddElementReq requestBody){
        return elementService.add(requestBody);
    }
}

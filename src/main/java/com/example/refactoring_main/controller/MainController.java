package com.example.refactoring_main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class MainController {

    //
    @GetMapping("/main")
    public ResponseEntity<Map<String,String>> main() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "John Doe");
        data.put("email", "johndoe@example.com");

        log.info("##################MAIN####################");

        return ResponseEntity.ok(data);
    }

}

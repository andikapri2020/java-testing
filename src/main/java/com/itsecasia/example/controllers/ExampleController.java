package com.itsecasia.example.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    
    @GetMapping(path = "hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWorld() {
        return "{\"key\":\"Hello World!\"}";
    }

    @GetMapping(path = "sleep/{time}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sleep(@PathVariable int time) {
        try {
            Thread.sleep(time);
            return String.format("{\"key\":\"Sleep for %d ms\"}", time);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return "{\"key\":\"QThread interrupted!\"}";
        }
    }


}

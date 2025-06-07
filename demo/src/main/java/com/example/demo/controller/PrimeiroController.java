package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiro")
public class PrimeiroController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}

// This is a simple Spring Boot controller that handles requests to the "/primeiro/hello" endpoint.

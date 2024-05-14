package com.eledevo.integration.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping("/world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/eledevo")
    @PreAuthorize("hasRole('MANAGER')") // Dùng để check role
    public String helloEledevo() {
        return "Hello Eledevo";
    }

    @GetMapping("/vietnam")
    @PreAuthorize("hasAuthority('management:read')")
    public String helloVietNam() {
        return "Hello Việt Nam";
    }

}

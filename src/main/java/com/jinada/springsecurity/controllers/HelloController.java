package com.jinada.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {
    @GetMapping("/unsecured")
    public String unsecured() {
        return "unsecured";
    }

    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        return "username: " + principal.getName();
    }
}

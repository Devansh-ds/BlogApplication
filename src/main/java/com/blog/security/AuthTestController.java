package com.blog.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AuthTestController {

    @GetMapping("/admin")
    public String admin() {
        return "admin from secured end point";
    }

    @GetMapping("/user")
    public String user() {
        return "user from secured end point";
    }



}

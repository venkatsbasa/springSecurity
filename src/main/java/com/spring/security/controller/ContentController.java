package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/home")
    public String handleWelcome() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        System.out.println("handleAdminHome");
        return "home_admin";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        System.out.println("handleUserHome");
        return "home_user";
    }

    @GetMapping("/login")
    public String handleLogin() {
        return "custom_login";
    }
}

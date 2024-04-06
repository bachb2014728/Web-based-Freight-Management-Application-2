package com.dev.backend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/login")
    public String loginAdmin(){
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}

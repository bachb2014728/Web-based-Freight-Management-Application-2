package com.dev.backend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/errors")
public class ErrorController {
    @GetMapping("")
    public String error(@RequestParam("error") String error, @RequestParam("url") String url, Model model){
        model.addAttribute("message",error);
        model.addAttribute("url",url);
        return "/error/error";
    }
}

package com.dev.backend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {
    @GetMapping("/list")
    public String getAllMerchandise(Model model){
        return "/merchandise/merchandise-list";
    }
    @GetMapping("/create")
    public String createNewMerchandise(Model model){
        return "/merchandise/merchandise-create";
    }
    @PostMapping("/save")
    public String saveMerchandise(){
        return "redirect:/merchandise/list";
    }
    @GetMapping("/{id}")
    public String getOneMerchandise(){
        return "/merchandise/merchandise-show";
    }
    @GetMapping("/{id}/edit")
    public String editOneMerchandise(){
        return "/merchandise/merchandise-update";
    }
    @PutMapping("/{id}/update")
    public String updateOneMerchandise(){
        return "redirect:/${id}/edit";
    }
    @DeleteMapping("/{id}/delete")
    public String deleteOneMerchandise(){
        return "redirect:/merchandise/merchandise-list";
    }
}

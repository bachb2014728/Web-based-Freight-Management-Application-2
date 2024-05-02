package com.dev.backend.web.controller;

import com.dev.backend.service.MerchandiseService;
import com.dev.backend.web.dto.EditMerchandise;
import com.dev.backend.web.dto.MerchandiseCreate;
import com.dev.backend.web.dto.MerchandiseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/merchandises")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;
    @GetMapping("")
    public String getAllMerchandise(Model model, Principal principal){
        List<MerchandiseDto> merchandises = merchandiseService.getAllMerchandise(principal);
        model.addAttribute("merchandises",merchandises);
        return "/merchandise/merchandise-list";
    }
    @GetMapping("/create")
    public String createNewMerchandise(Model model){
        MerchandiseCreate merchandise = MerchandiseCreate.builder().build();
        model.addAttribute("merchandise" ,merchandise);
        return "/merchandise/merchandise-create";
    }
    @PostMapping("/save")
    public String saveMerchandise(@ModelAttribute("merchandise") MerchandiseCreate merchandise,
                                  @RequestParam("photos") MultipartFile[] photos
    ) throws IOException {
//        merchandiseService.saveNewMerchandise(merchandise,photos);
        return "redirect:/merchandises";
    }
    @GetMapping("/{id}")
    public String getOneMerchandise(@PathVariable String id, Model model){
        MerchandiseDto merchandise = merchandiseService.getOneMerchandise(id);
        model.addAttribute("merchandise", merchandise);
        return "/merchandise/merchandise-show";
    }
    @GetMapping("/{id}/edit")
    public String editOneMerchandise(@PathVariable String id, Model model){
        EditMerchandise merchandise = merchandiseService.getOneMerchandiseEdit(id);
        model.addAttribute("merchandise", merchandise);
        model.addAttribute("id",id);
        return "/merchandise/merchandise-update";
    }
    @PostMapping("/{id}/update")
    public String updateOneMerchandise(@PathVariable String id,
                                       @ModelAttribute("merchandise") EditMerchandise merchandise,
                                       @RequestParam("photos") MultipartFile[] photos) throws IOException{
        merchandiseService.updateMerchandise(merchandise, id, photos);
        String slug = "/merchandises/"+id+"/edit";
        return "redirect:"+slug;
    }
    @GetMapping("/{id}/delete")
    public String deleteOneMerchandise(@PathVariable String id){
        merchandiseService.deleteOneMerchandise(id);
        return "redirect:/merchandises";
    }
}

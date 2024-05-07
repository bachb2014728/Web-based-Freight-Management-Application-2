package com.dev.backend.web.controller;

import com.dev.backend.service.MerchandiseService;
import com.dev.backend.web.dto.EditMerchandise;
import com.dev.backend.web.dto.MerchandiseCreate;
import com.dev.backend.web.dto.MerchandiseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/merchandises")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;
    @GetMapping("")
    public String getAllMerchandise(Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            List<MerchandiseDto> merchandises = merchandiseService.getAllMerchandise(principal);
            model.addAttribute("merchandises",merchandises);
            return "/merchandise/merchandise-list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/create")
    public String createNewMerchandise(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            MerchandiseCreate merchandise = MerchandiseCreate.builder().build();
            model.addAttribute("merchandise" ,merchandise);
            return "/merchandise/merchandise-create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/save")
    public String saveMerchandise(@ModelAttribute("merchandise") MerchandiseCreate merchandise,
                                  @RequestParam("photos") MultipartFile[] photos,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes
    ) throws IOException {
        try {
//            merchandiseService.saveNewMerchandise(merchandise,photos);
            return "redirect:/merchandises";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/{id}")
    public String getOneMerchandise(
            @PathVariable String id, Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request){
        try {
            MerchandiseDto merchandise = merchandiseService.getOneMerchandise(id);
            model.addAttribute("merchandise", merchandise);
            return "/merchandise/merchandise-show";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/{id}/edit")
    public String editOneMerchandise(
            @PathVariable String id, Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ){
        try {
            EditMerchandise merchandise = merchandiseService.getOneMerchandiseEdit(id);
            model.addAttribute("merchandise", merchandise);
            model.addAttribute("id",id);
            return "/merchandise/merchandise-update";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/{id}/update")
    public String updateOneMerchandise(@PathVariable String id,
                                       @ModelAttribute("merchandise") EditMerchandise merchandise,
                                       @RequestParam("photos") MultipartFile[] photos,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes
    ) throws IOException{
        try {
            merchandiseService.updateMerchandise(merchandise, id, photos);
            String slug = "/merchandises/"+id+"/edit";
            return "redirect:"+slug;
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/{id}/delete")
    public String deleteOneMerchandise(@PathVariable String id, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            merchandiseService.deleteOneMerchandise(id);
            return "redirect:/merchandises";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
}

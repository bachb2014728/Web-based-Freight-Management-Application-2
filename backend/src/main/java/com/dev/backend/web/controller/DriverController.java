package com.dev.backend.web.controller;

import com.dev.backend.document.Car;
import com.dev.backend.document.Driver;
import com.dev.backend.service.CarService;
import com.dev.backend.service.DriverService;
import com.dev.backend.web.dto.CarDto;
import com.dev.backend.web.dto.DriverDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final CarService carService;
    @GetMapping("")
    public String getAllDriver(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            List<DriverDto> drivers = driverService.all();
            model.addAttribute("drivers", drivers);
            return "driver/driver-list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }

    }
    @GetMapping("/create")
    public String createDriver(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            Driver driver = new Driver();
            List<CarDto> cars = carService.getAvailable();
            model.addAttribute("driver",driver);
            model.addAttribute("cars",cars);
            return "driver/driver-create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/save")
    public String saveDriver(@ModelAttribute("driver") DriverDto driverDto, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            driverService.save(driverDto);
            return "redirect:/drivers";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }

    @GetMapping("/{driverId}/edit")
    public String edit(@PathVariable("driverId") String driverId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            DriverDto driver = driverService.one(driverId);
            List<CarDto> cars = carService.all();
            model.addAttribute("driver",driver);
            model.addAttribute("cars",cars);
            return "driver/driver-update";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }

    @PostMapping("/{driverId}/update")
    public String update(
            @PathVariable("driverId") String driverId,
            @ModelAttribute("driver") DriverDto driverDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ){
       try {
           driverService.update(driverId,driverDto);
           String slug = "/drivers/"+driverId+"/edit";
           return "redirect:"+slug;
       }catch (Exception e){
           System.out.println(e.getMessage());
           redirectAttributes.addAttribute("error", e.getMessage());
           redirectAttributes.addAttribute("url", request.getHeader("Referer"));
           return "redirect:/errors";
       }
    }
    @GetMapping("/{driverId}/delete")
    public String delete(@PathVariable("driverId") String driverId,HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            driverService.delete(driverId);
            return "redirect:/drivers";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
}

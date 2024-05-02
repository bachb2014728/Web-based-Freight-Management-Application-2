package com.dev.backend.web.controller;

import com.dev.backend.document.Car;
import com.dev.backend.document.Driver;
import com.dev.backend.service.CarService;
import com.dev.backend.service.DriverService;
import com.dev.backend.web.dto.CarDto;
import com.dev.backend.web.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final CarService carService;
    @GetMapping("")
    public String getAllDriver(Model model){
        List<DriverDto> drivers = driverService.all();
        model.addAttribute("drivers", drivers);
        return "driver/driver-list";
    }
    @GetMapping("/create")
    public String createDriver(Model model){
        Driver driver = new Driver();
        List<CarDto> cars = carService.getAvailable();
        model.addAttribute("driver",driver);
        model.addAttribute("cars",cars);
        return "driver/driver-create";
    }
    @PostMapping("/save")
    public String saveDriver(@ModelAttribute("driver") DriverDto driverDto){
        driverService.save(driverDto);
        return "redirect:/drivers";
    }

    @GetMapping("/{driverId}/edit")
    public String edit(@PathVariable("driverId") String driverId, Model model){
        DriverDto driver = driverService.one(driverId);
        List<CarDto> cars = carService.all();
        model.addAttribute("driver",driver);
        model.addAttribute("cars",cars);
        return "driver/driver-update";
    }

    @PostMapping("/{driverId}/update")
    public String update(@PathVariable("driverId") String driverId, @ModelAttribute("driver") DriverDto driverDto){
        driverService.update(driverId,driverDto);
        String slug = "/drivers/"+driverId+"/edit";
        return "redirect:"+slug;
    }
    @GetMapping("/{driverId}/delete")
    public String delete(@PathVariable("driverId") String driverId){
        driverService.delete(driverId);
        return "redirect:/drivers";
    }
}

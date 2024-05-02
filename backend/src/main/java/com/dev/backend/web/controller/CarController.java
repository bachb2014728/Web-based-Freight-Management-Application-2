package com.dev.backend.web.controller;

import com.dev.backend.document.Car;
import com.dev.backend.service.CarService;
import com.dev.backend.web.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @GetMapping("")
    public String getAllCar(Model model){
        List<CarDto> cars = carService.all();
        model.addAttribute("cars", cars);
        return "car/car-list";
    }

    @GetMapping("/create")
    public String createCar(Model model){
        CarDto car = CarDto.builder().build();
        model.addAttribute("car", car);
        return "car/car-create";
    }
    @PostMapping("/save")
    public String saveCar(@ModelAttribute("car") CarDto carDto, Model model){
        carService.save(carDto);
        return "redirect:/cars";
    }

    @GetMapping("/{carId}/edit")
    public String edit(Model model, @PathVariable("carId") String carId){
        CarDto carDto = carService.findOne(carId);
        model.addAttribute("car", carDto);
        return "car/car-update";
    }
    @PostMapping("/{carId}/update")
    public String update(@PathVariable("carId") String carId, @ModelAttribute("car") CarDto carDto){
        carService.update(carDto, carId);
        String slug = "/cars/"+carId+"/edit";
        return "redirect:"+slug;
    }
    @GetMapping("/{carId}/delete")
    public String delete(@PathVariable("carId") String carId){
        carService.delete(carId);
        return "redirect:/cars";
    }
}

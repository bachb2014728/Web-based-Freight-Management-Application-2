package com.dev.backend.web.controller;

import com.dev.backend.service.ShipmentService;
import com.dev.backend.web.dto.ShipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ShipmentService shipmentService;
    @GetMapping("/login")
    public String loginAdmin(){
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        List<ShipmentDto> shipments = shipmentService.findAllShipmentHaveAwaitingExportAndReceiverStore(principal);
        model.addAttribute("shipments", shipments);
        return "dashboard";
    }
}

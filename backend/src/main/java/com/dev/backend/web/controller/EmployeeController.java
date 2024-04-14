package com.dev.backend.web.controller;

import com.dev.backend.document.UserDocument;
import com.dev.backend.service.EmployeeService;
import com.dev.backend.service.UserService;
import com.dev.backend.web.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final UserService userService;
    private final EmployeeService employeeService;
    @GetMapping("")
    private String getAllEmployee(Model model, Principal principal){
        List<EmployeeDto> employees = employeeService.getEmployess(principal);
        model.addAttribute("employees", employees);
        return "employee/employee-list";
    }
    @GetMapping("/create")
    private String createOneEmployee(Model model){
        UserDocument user = new UserDocument();
        model.addAttribute("employee",user);
        return "employee/employee-create";
    }
    @PostMapping("/create")
    public String saveEmployeeNew(@ModelAttribute("employee") UserDocument document, Model model){
        return "redirect:/employee/list";
    }
    @GetMapping("/{id}")
    public String getOneEmployee(){
        return "/employee/employee-show";
    }
    @GetMapping("/{id}/edit")
    public String editOneEmployee(){
        return "/employee/employee-update";
    }
    @PutMapping("/{id}/update")
    public String updateOneEmployee(){
        return "redirect:/";
    }
    @DeleteMapping("/{id}/delete")
    public String deleteOneEmployee(){
        return "redirect:/employee/list";
    }
}

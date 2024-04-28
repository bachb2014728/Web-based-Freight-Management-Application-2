package com.dev.backend.web.controller;

import com.dev.backend.document.Role;
import com.dev.backend.service.EmployeeService;
import com.dev.backend.service.RoleService;
import com.dev.backend.web.dto.CreateEmployee;
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
    private final EmployeeService employeeService;
    private final RoleService roleService;
    @GetMapping("")
    private String getAllEmployee(Model model, Principal principal){
        List<EmployeeDto> employees = employeeService.getEmployees(principal);
        model.addAttribute("employees", employees);
        return "employee/employee-list";
    }
    @GetMapping("/create")
    private String createOneEmployee(Model model){
        CreateEmployee employee = CreateEmployee.builder().build();
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("employee",employee);
        model.addAttribute("roles", roles);
        return "employee/employee-create";
    }
    @PostMapping("/create")
    public String saveEmployeeNew(@ModelAttribute("employee") CreateEmployee employee, Model model, Principal principal){
        employeeService.saveEmployee(employee, principal);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("employee",employee);
        model.addAttribute("roles", roles);
        return "redirect:/employees/create";
    }
    @GetMapping("/{id}")
    public String getOneEmployee(@PathVariable String id,Model model){
        EmployeeDto employeeDto = employeeService.getEmployeeShow(id);
        model.addAttribute("employee", employeeDto);
        return "/employee/employee-show";
    }
    @GetMapping("/{id}/edit")
    public String editOneEmployee(@PathVariable String id,Model model){
        EmployeeDto employee = employeeService.getEmployeeShow(id);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        model.addAttribute("id", id);
        model.addAttribute("employee", employee);
        return "/employee/employee-update";
    }
    @PostMapping("/{id}/update")
    public String updateOneEmployee(@PathVariable String id, @ModelAttribute("employee") CreateEmployee employeeUpdate){
        employeeService.updateEmployee(id,employeeUpdate);
        String slug = "/employees/"+id+"/edit";
        return "redirect:"+slug;
    }
    @DeleteMapping("/{id}/delete")
    public String deleteOneEmployee(@PathVariable String id){
        employeeService.removeEmployee(id);
        return "redirect:/employee/list";
    }
}

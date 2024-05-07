package com.dev.backend.web.controller;

import com.dev.backend.document.Role;
import com.dev.backend.service.EmployeeService;
import com.dev.backend.service.RoleService;
import com.dev.backend.service.helper.ConvertUser;
import com.dev.backend.web.dto.CreateEmployee;
import com.dev.backend.web.dto.EmployeeDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final ConvertUser convertUser;
    @GetMapping("")
    private String getAllEmployee(Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            List<EmployeeDto> employees = employeeService.getEmployees(principal);
            model.addAttribute("employees", employees);
            return "employee/employee-list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/create")
    private String createOneEmployee(Model model,RedirectAttributes redirectAttributes,HttpServletRequest request){
        try {
            CreateEmployee employee = CreateEmployee.builder().build();
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("employee",employee);
            model.addAttribute("roles", roles);
            return "employee/employee-create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/create")
    public String saveEmployeeNew(
            @ModelAttribute("employee") CreateEmployee employee,
            Model model, Principal principal,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ){
        try {
            employeeService.saveEmployee(employee, principal);
            return "redirect:/employees/create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/{id}")
    public String getOneEmployee(@PathVariable String id,Model model,RedirectAttributes redirectAttributes,HttpServletRequest request){
        try {
            EmployeeDto employeeDto = employeeService.getEmployeeShow(id);
            model.addAttribute("employee", employeeDto);
            return "/employee/employee-show";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/{id}/edit")
    public String editOneEmployee(@PathVariable String id,Model model,Principal principal,RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            EmployeeDto employee = employeeService.getEmployeeShow(id);
            List<Role> roles = roleService.getAllRole();

            model.addAttribute("role",convertUser.getRoleFromEmail(principal.getName()));
            model.addAttribute("roles", roles);
            model.addAttribute("id", id);
            model.addAttribute("employee", employee);
            return "/employee/employee-update";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/{id}/update")
    public String updateOneEmployee(
            @PathVariable String id,
            @ModelAttribute("employee") CreateEmployee employeeUpdate,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes){
        try {
            employeeService.updateEmployee(id,employeeUpdate);
            String slug = "/employees/"+id+"/edit";
            return "redirect:"+slug;
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @DeleteMapping("/{id}/delete")
    public String deleteOneEmployee(@PathVariable String id,RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            employeeService.removeEmployee(id);
            return "redirect:/employee/list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
}

package com.dev.backend.web.controller;

import com.dev.backend.document.Customer;
import com.dev.backend.service.MemberService;
import com.dev.backend.web.dto.CreateMember;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("")
    public String getAllMemberInProvince(Model model, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            List<Customer> customerList= memberService.getAllMemberInProvince(principal);
            System.out.print(customerList);
            model.addAttribute("customers",customerList);
            return "/member/member-list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/create")
    public String create(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            CreateMember createMember = CreateMember.builder().build();
            model.addAttribute("member",createMember);
            return "/member/member-create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/create")
    public String save(@ModelAttribute("member") CreateMember member, Principal principal){
        return "redirect:/members";
    }
    @GetMapping("/{memberId}")
    public String getOne(@PathVariable("memberId") String memberId, Model model){
        return "member/member-show";
    }
}

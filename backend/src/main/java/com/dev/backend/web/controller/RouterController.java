package com.dev.backend.web.controller;

import com.dev.backend.document.Router;
import com.dev.backend.service.RouterService;
import com.dev.backend.web.dto.RouterDto;
import com.dev.backend.web.dto.RouterRequest;
import com.dev.backend.web.util.RandomId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class RouterController {
    private final RouterService routerService;
    @GetMapping("/routers")
    public String listRouters(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            List<RouterDto> routers = routerService.findAllRouters();
            model.addAttribute("routers",routers);
            return "router/router-list";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("/routers/create")
    public String createRouterForm(Model model,HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Router router = new Router();
            String Id = RandomId.randomId();
            model.addAttribute("id",Id);
            model.addAttribute("router",router);
            return "router/router-create";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("/routers/create")
    public String saveRouterNew(@ModelAttribute("router") RouterRequest routerRequest, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            routerService.saveRouter(routerRequest);
            return "redirect:/routers";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("routers/{routerId}/delete")
    public String deleteRouter(@PathVariable("routerId")String routerId, RedirectAttributes redirectAttributes, HttpServletRequest request){
        try {
            routerService.deleteRouterById(routerId);
            return "redirect:/routers";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("routers/{routerId}")
    public String showRouter(
            @PathVariable("routerId")String routerId,Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ){
        try {
            RouterDto router = routerService.findRouterById(routerId);
            model.addAttribute("router",router);
            return "router/router-show";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @GetMapping("routers/{routerId}/edit")
    public String editRouterForm(
            @PathVariable("routerId")String routerId,Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ){
        try {
            RouterDto routerDto = routerService.findRouterById(routerId);
            model.addAttribute("router",routerDto);
            return "router/router-update";
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
    @PostMapping("routers/{routerId}/update")
    public String updateRouter(
            @PathVariable("routerId")String routerId,
            @ModelAttribute("router") RouterRequest routerRequest, Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ){
        try {
            RouterDto router = routerService.findRouterById(routerId);
            if(router != null){
                RouterDto routerUp = routerService.updateRouter(routerRequest,routerId);
                model.addAttribute("router", routerUp);
            }else{
                model.addAttribute("router", routerRequest);
            }
            String slug = "/routers/"+routerId+"/edit";
            return "redirect:"+slug;
        }catch (Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("url", request.getHeader("Referer"));
            return "redirect:/errors";
        }
    }
}

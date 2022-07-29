package com.nnk.springboot.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome() {
        return "redirect:/admin/bidList/list";
    }


}

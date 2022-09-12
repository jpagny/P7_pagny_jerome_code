package com.nnk.springboot.controller;


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

    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "redirect:/home";
    }

    @RequestMapping("/admin/home")
    public String adminHome() {
        return "redirect:/admin/bidList/list";
    }

    @RequestMapping("/user/home")
    public String userHome() {
        return "homeUser";
    }


}

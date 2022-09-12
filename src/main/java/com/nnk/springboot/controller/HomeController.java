package com.nnk.springboot.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@AllArgsConstructor

public class HomeController {

    @RequestMapping(value = "/home")
    public String home(Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

            for (final GrantedAuthority grantedAuthority : authorities) {

                String authorityName = grantedAuthority.getAuthority();

                switch (authorityName) {
                    case "ROLE_ADMIN":
                        return "redirect:/admin/home";

                    case "ROLE_USER":
                        return "redirect:/user/home";

                    default:
                        return "redirect:/error/";
                }

            }
        }

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

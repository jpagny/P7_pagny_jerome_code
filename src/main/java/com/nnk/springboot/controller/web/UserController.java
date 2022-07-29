package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.service.implement.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/admin")
@AllArgsConstructor
@Controller
public class UserController {

    private static final Logger LOG = LogManager.getLogger("UserController");
    private final UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        List<UserEntity> listUser = userService.findAll();
        model.addAttribute("pageTitle", "User - list");
        model.addAttribute("listUser", listUser);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserDTO userDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/add";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        userService.create(userDTO);

        model.addAttribute("users", userService.findAll());

        return "redirect:/admin/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            UserDTO userDTO = userService.findById(id);
            model.addAttribute("userDTO", userDTO);
        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDTO, BindingResult result, Model model) {

        try {
            if (result.hasErrors()) {
                return "user/update";
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));

            userService.create(userDTO);
            model.addAttribute("listUser", userService.findAll());

        } catch (Exception ex){
            LOG.error("Exception : " + ex);
        }

        return "redirect:/admin/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.delete(id);

        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }

        return "redirect:/admin/user/list";
    }
}

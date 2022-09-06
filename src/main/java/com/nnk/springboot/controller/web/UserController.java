package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.entity.UserEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.implement.UserService;
import lombok.AllArgsConstructor;
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
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("userDTO", userDTO);

        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDTO, BindingResult result, Model model) throws ResourceNotFoundException {

        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        userService.update(id, userDTO);
        model.addAttribute("listUser", userService.findAll());

        return "redirect:/admin/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        userService.delete(id);

        return "redirect:/admin/user/list";
    }
}

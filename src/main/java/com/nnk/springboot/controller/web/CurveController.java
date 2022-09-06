package com.nnk.springboot.controller.web;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.implement.CurvePointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/admin")
@Controller
@AllArgsConstructor

public class CurveController implements WebMvcConfigurer {
    private final CurvePointService curvePointService;

    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePointDTO> listCurvePointEntity = curvePointService.findAll();
        model.addAttribute("pageTitle", "CurvePoint - list");
        model.addAttribute("listCurvePoints", listCurvePointEntity);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePointDTO", new CurvePointDTO());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO curvePointDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.create(curvePointDTO);

        model.addAttribute("pageTitle", "CurvePoint - list");
        model.addAttribute("listCurvePoints", curvePointService.findAll());

        return "redirect:/admin/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {

        CurvePointDTO curvePoint = curvePointService.findById(id);
        model.addAttribute("curvePointDTO", curvePoint);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                                   BindingResult result, Model model) throws ResourceNotFoundException {

        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.update(id, curvePointDTO);
        model.addAttribute("curvePoints", curvePointService.findAll());

        return "redirect:/admin/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        curvePointService.delete(id);
        return "redirect:/admin/curvePoint/list";
    }
}


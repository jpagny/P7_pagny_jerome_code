package com.nnk.springboot.controller.web;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.service.implement.CurvePointService;
import dto.CurvePointDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
        List<CurvePointEntity> listCurvePointEntity = curvePointService.findAll();
        model.addAttribute("pageTitle", "CurvePoint - list");
        model.addAttribute("listCurvePoints", listCurvePointEntity);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePointEntity curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointEntity curvePointEntity, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.create(curvePointEntity);

        model.addAttribute("pageTitle", "CurvePoint - list");
        model.addAttribute("listCurvePoints", curvePointService.findAll());

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, CurvePointEntity curvePointEntity,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}


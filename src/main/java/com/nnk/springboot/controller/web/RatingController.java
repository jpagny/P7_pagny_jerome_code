package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.implement.RatingService;
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
@AllArgsConstructor
@Controller
public class RatingController implements WebMvcConfigurer {
    private final RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<RatingEntity> listRatingEntity = ratingService.findAll();
        model.addAttribute("pageTitle", "Rating - list");
        model.addAttribute("listRating", listRatingEntity);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("ratingDTO", new RatingDTO());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO ratingDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.create(ratingDTO);

        model.addAttribute("pageTitle", "Rating - list");
        model.addAttribute("listRating", ratingService.findAll());
        return "redirect:/admin/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {

        RatingDTO rating = ratingService.findById(id);
        model.addAttribute("ratingDTO", rating);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                               BindingResult result, Model model) throws ResourceNotFoundException {

        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.update(id, ratingDTO);

        model.addAttribute("curvePoints", ratingService.findAll());

        return "redirect:/admin/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        ratingService.delete(id);
        return "redirect:/admin/rating/list";
    }
}

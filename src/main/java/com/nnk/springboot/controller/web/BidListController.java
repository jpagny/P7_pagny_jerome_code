package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.service.implement.BidListService;
import lombok.AllArgsConstructor;
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
@Controller
@AllArgsConstructor
public class BidListController {
    private final BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidListEntity> bidListEntityList = bidListService.findAll();
        model.addAttribute("pageTitle", "BidList - list");
        model.addAttribute("listBidList", bidListEntityList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidListDTO", new BidListDTO());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO bidListDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.create(bidListDTO);

        model.addAttribute("pageTitle", "Bid list - list");
        model.addAttribute("listBidList", bidListService.findAll());

        return "redirect:/admin/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {

        BidListDTO bidList = bidListService.findById(id);
        model.addAttribute("bidListDTO", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                            BindingResult result, Model model) throws ResourceNotFoundException {

        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.update(id, bidListDTO);

        model.addAttribute("listBidList", bidListService.findAll());

        return "redirect:/admin/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        bidListService.delete(id);
        return "redirect:/admin/bidList/list";
    }
}

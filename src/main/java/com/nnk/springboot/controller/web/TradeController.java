package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.service.implement.TradeService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class TradeController implements WebMvcConfigurer {

    private static final Logger LOG = LogManager.getLogger("TradeController");
    private final TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<TradeEntity> listTrade = tradeService.findAll();
        model.addAttribute("pageTitle", "CurvePoint - list");
        model.addAttribute("listTrade", listTrade);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("tradeDTO", new TradeDTO());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO tradeDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.create(tradeDTO);

        model.addAttribute("pageTitle", "Trade - list");
        model.addAttribute("listTrade", tradeService.findAll());

        return "redirect:/admin/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        try {
            TradeDTO tradeDTO = tradeService.findById(id);
            model.addAttribute("tradeDTO", tradeDTO);
        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                              BindingResult result, Model model) {
        try {

            if (result.hasErrors()) {
                return "trade/update";
            }

            tradeService.update(id, tradeDTO);

            model.addAttribute("listTrade", tradeService.findAll());


        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }

        return "redirect:/admin/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        try {
            tradeService.delete(id);

        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }

        return "redirect:/admin/trade/list";
    }
}


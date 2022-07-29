package com.nnk.springboot.controller.web;


import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.service.implement.RuleNameService;
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
public class RuleNameController implements WebMvcConfigurer {

    private static final Logger LOG = LogManager.getLogger("CurveController");
    private final RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleNameEntity> listRuleNameEntity = ruleNameService.findAll();
        model.addAttribute("pageTitle", "Rule name - list");
        model.addAttribute("listRuleName", listRuleNameEntity);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleNameDTO", new RuleNameDTO());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDTO ruleNameDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.create(ruleNameDTO);

        model.addAttribute("pageTitle", "Rule name - list");
        model.addAttribute("listCurvePoints", ruleNameService.findAll());

        return "redirect:/admin/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleNameDTO ruleName = ruleNameService.findById(id);
            model.addAttribute("ruleNameDTO", ruleName);
        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleNameDTO,
                                 BindingResult result, Model model) {
        try {

            if (result.hasErrors()) {
                return "ruleName/update";
            }

            ruleNameService.update(id, ruleNameDTO);

        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }

        model.addAttribute("listRuleName", ruleNameService.findAll());
        return "redirect:/admin/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        try {
            ruleNameService.delete(id);

        } catch (Exception ex) {
            LOG.error("Exception :" + ex);
        }
        return "redirect:/admin/ruleName/list";
    }
}

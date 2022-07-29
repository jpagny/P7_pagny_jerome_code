package com.nnk.springboot.service;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IRuleNameService {

    RuleNameDTO findById(Integer id) throws ResourceNotFoundException;

    List<RuleNameEntity> findAll();

    RuleNameDTO create(RuleNameDTO ruleNameDTO);

    RuleNameDTO update(Integer id, RuleNameDTO ruleNameDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

}

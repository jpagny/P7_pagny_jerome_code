package com.nnk.springboot.service;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface ITradeService {

    TradeDTO findById(Integer id) throws ResourceNotFoundException;

    List<TradeEntity> findAll();

    TradeDTO create(TradeDTO tradeDTO);

    TradeDTO update(Integer id, TradeDTO tradeDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}

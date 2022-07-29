package com.nnk.springboot.service;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IBidListService {

    BidListDTO findById(Integer id) throws ResourceNotFoundException;

    List<BidListEntity> findAll();

    BidListDTO create(BidListDTO bidListDTO);

    BidListDTO update(Integer id, BidListDTO bidListDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}

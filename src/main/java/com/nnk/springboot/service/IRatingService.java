package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IRatingService {

    RatingDTO findById(Integer id) throws ResourceNotFoundException;

    List<RatingEntity> findAll();

    RatingDTO create(RatingDTO ratingDTO);

    RatingDTO update(Integer id, RatingDTO ratingDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}

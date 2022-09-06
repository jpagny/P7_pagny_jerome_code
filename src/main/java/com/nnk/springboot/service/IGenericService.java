package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IGenericService<T> {

    T findById(Integer id) throws ResourceNotFoundException;
    List<T> findAll();
    T create(T dto);
    T update(Integer id, T dto) throws ResourceNotFoundException;
    void delete(Integer id) throws ResourceNotFoundException;


}

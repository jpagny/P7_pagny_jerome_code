package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface IGenericService<DTO> {
    DTO findById(Integer id) throws ResourceNotFoundException;
    List<DTO> findAll();
    DTO create(DTO dto);
    DTO update(Integer id, DTO dto) throws ResourceNotFoundException;
    void delete(Integer id) throws ResourceNotFoundException, NoSuchMethodException;

}

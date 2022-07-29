package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import dto.CurvePointDTO;

import java.util.List;

public interface ICurvePointService {

    CurvePointDTO findById(Integer id) throws ResourceNotFoundException;

    List<CurvePointEntity> findAll();

    CurvePointDTO create(CurvePointDTO curvePointDTO);

    CurvePointDTO update(Integer id, CurvePointDTO curvePointDTO) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;
}

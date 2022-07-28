package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;

import java.util.List;

public interface ICurvePointService {

    List<CurvePointEntity> findAll();

    CurvePointEntity create(CurvePointEntity curvePointEntity);

    CurvePointEntity update(CurvePointEntity curvePointEntity) throws ResourceNotFoundException;

    void delete(CurvePointEntity curvePointEntity);
}

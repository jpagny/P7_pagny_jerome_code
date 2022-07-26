package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;

import java.util.List;

public interface ICurvePointService {

    List<CurvePointEntity> findAll();
}

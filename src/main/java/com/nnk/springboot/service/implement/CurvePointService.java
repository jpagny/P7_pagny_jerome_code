package com.nnk.springboot.service.implement;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.ICurvePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurvePointService implements ICurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePointEntity> findAll() {
        return curvePointRepository.findAll();
    }
}

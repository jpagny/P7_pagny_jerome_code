package com.nnk.springboot.service.implement;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.ICurvePointService;
import dto.CurvePointDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurvePointService implements ICurvePointService {

    private final CurvePointRepository curvePointRepository;
    private final ModelMapper modelMapper;


    @Override
    public CurvePointDTO findById(Integer id) throws ResourceNotFoundException {

        CurvePointEntity curvePointEntity = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist with id : " + id));

        CurvePointDTO curvePointDTO = modelMapper.map(curvePointEntity, CurvePointDTO.class);

        return curvePointDTO;
    }

    @Override
    public List<CurvePointEntity> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePointEntity create(CurvePointEntity curvePointEntity) {
        return curvePointRepository.save(curvePointEntity);
    }

    @Override
    public CurvePointDTO update(Integer id, CurvePointDTO curvePointDTO) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist : " + id));

        Integer curveId = curvePointDTO.getCurveId() != null
                ? curvePointDTO.getCurveId()
                : curvePointFound.getCurveId();

        Double term = curvePointDTO.getTerm() != null
                ? curvePointDTO.getTerm()
                : curvePointFound.getTerm();

        Double value = curvePointDTO.getValue() != null
                ? curvePointDTO.getValue()
                : curvePointFound.getValue();

        curvePointFound.setCurveId(curveId);
        curvePointFound.setTerm(term);
        curvePointFound.setValue(value);

        curvePointFound.setCreationDate(Timestamp.from(Instant.now()));

        curvePointRepository.save(curvePointFound);

        return modelMapper.map(curvePointFound,CurvePointDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist : " + id));

        curvePointRepository.delete(curvePointFound);
    }


}

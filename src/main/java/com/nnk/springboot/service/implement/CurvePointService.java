package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.IGenericService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CurvePointService implements IGenericService<CurvePointDTO> {

    private final CurvePointRepository curvePointRepository;
    private final ModelMapper modelMapper;

    @Override
    public CurvePointDTO findById(Integer id) throws ResourceNotFoundException {

        CurvePointEntity curvePointEntity = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist with id : " + id));

        return modelMapper.map(curvePointEntity, CurvePointDTO.class);
    }

    @Override
    public List<CurvePointDTO> findAll() {
        return curvePointRepository.findAll().stream()
                .map(curvePoint -> modelMapper.map(curvePoint, CurvePointDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CurvePointDTO create(CurvePointDTO curvePointDTO) {

        CurvePointEntity curvePointEntity = modelMapper.map(curvePointDTO, CurvePointEntity.class);
        CurvePointEntity curvePointEntitySaved = curvePointRepository.save(curvePointEntity);

        return modelMapper.map(curvePointEntitySaved, CurvePointDTO.class);
    }

    @Override
    public CurvePointDTO update(Integer id, CurvePointDTO curvePointDTO) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist with id : " + id));

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

        curvePointRepository.save(curvePointFound);

        return modelMapper.map(curvePointFound, CurvePointDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist with id : " + id));

        curvePointRepository.delete(curvePointFound);
    }


}

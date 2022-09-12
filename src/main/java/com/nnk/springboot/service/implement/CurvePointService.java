package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.CurvePointRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CurvePointService extends AbstractServiceCrud<CurvePointEntity, CurvePointDTO> {

    private final CurvePointRepository curvePointRepository;
    private final ModelMapper modelMapper;

    public CurvePointService(CurvePointRepository theCurvePointRepository) {
        super(theCurvePointRepository);
        this.modelMapper = new ModelMapper();
        this.curvePointRepository = theCurvePointRepository;
    }

    @Override
    public CurvePointDTO update(Integer id, CurvePointDTO curvePointDTO) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

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

}

package com.nnk.springboot.service.implement;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.ICurvePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    @Override
    public CurvePointEntity create(CurvePointEntity curvePointEntity) {
        return curvePointRepository.save(curvePointEntity);
    }

    @Override
    public CurvePointEntity update(CurvePointEntity curvePointToUpdate) throws ResourceNotFoundException {

        CurvePointEntity curvePointFound = curvePointRepository.findById(curvePointToUpdate.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Curve point doesn't exist : " + curvePointToUpdate.getId()));

        /*
        Timestamp date = curvePointToUpdate.getAsOfDate() != null
                ? curvePointToUpdate.getAsOfDate()
                : curvePointFound.getAsOfDate();*/

        Double term = curvePointToUpdate.getTerm() != null
                ? curvePointToUpdate.getTerm()
                : curvePointFound.getTerm();

        Double value = curvePointToUpdate.getValue() != null
                ? curvePointToUpdate.getValue()
                : curvePointFound.getValue();

        //curvePointFound.setAsOfDate(date);
        curvePointFound.setTerm(term);
        curvePointFound.setValue(value);

        return curvePointFound;
    }

    @Override
    public void delete(CurvePointEntity curvePointToDelete) {
        curvePointRepository.delete(curvePointToDelete);
    }


}

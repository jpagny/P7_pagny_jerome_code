package com.nnk.springboot.repository;

import com.nnk.springboot.entity.CurvePointEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurvePointRepositoryTestIT {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void curvePointTest() {
        CurvePointEntity curvePoint = new CurvePointEntity(3,5d, 6d);

        // Save
        curvePoint = curvePointRepository.save(curvePoint);
        assertNotNull(curvePoint.getTerm());
        assertEquals(5d, curvePoint.getTerm());

        // Update
        curvePoint.setValue(10d);
        curvePoint = curvePointRepository.save(curvePoint);
        assertEquals(10d, curvePoint.getValue());

        // Find
        List<CurvePointEntity> listResult = curvePointRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePointEntity> theCurvePoint = curvePointRepository.findById(id);
        assertFalse(theCurvePoint.isPresent());
    }
}

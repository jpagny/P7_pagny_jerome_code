package com.nnk.springboot.repository;

import com.nnk.springboot.entity.CurvePointEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CurvePointEntityTest {
    @Mock
    private CurvePointRepository curvePointRepository;

    @Test
    public void should_beReturnedCurvePoint_when_aNewCurvePointIsSaved() {
        CurvePointEntity curvePointEntity = new CurvePointEntity(10, 10d, 30d);

        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(curvePointEntity);

        curvePointEntity = curvePointRepository.save(curvePointEntity);

        assertNotNull(curvePointEntity.getId());
        assertEquals(10, (int) curvePointEntity.getCurveId());
        assertNotNull(curvePointEntity.getCreationDate());
    }

    @Test
    public void should_beReturnedCurvePoint_when_aCurvePointIsUpdated() {
        CurvePointEntity curvePointEntity = new CurvePointEntity(10, 10d, 30d);
        curvePointEntity.setCurveId(20);
        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(curvePointEntity);

        curvePointEntity = curvePointRepository.save(curvePointEntity);

        assertNotNull(curvePointEntity.getId());
        assertEquals(20, (int) curvePointEntity.getCurveId());
        assertNotNull(curvePointEntity.getCreationDate());
    }

    @Test
    public void should_beReturnedAllCurvePoint_when_findAllIsCalled() {
        CurvePointEntity curvePointEntity = new CurvePointEntity(10, 10d, 30d);
        List<CurvePointEntity> listCurvePointEntity = new ArrayList<>();
        listCurvePointEntity.add(curvePointEntity);
        when(curvePointRepository.findAll()).thenReturn(listCurvePointEntity);

        List<CurvePointEntity> listResult = curvePointRepository.findAll();

        assertTrue(listResult.size() > 0);
    }

    @Test
    public void should_beCurvePointNotPresent_when_curvePointIsDeleted() {
        CurvePointEntity curvePointEntity = new CurvePointEntity(10, 10d, 30d);
        Integer id = curvePointEntity.getId();
        doNothing().when(curvePointRepository).delete(curvePointEntity);
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        curvePointRepository.delete(curvePointEntity);

        Optional<CurvePointEntity> curvePointList = curvePointRepository.findById(id);

        assertFalse(curvePointList.isPresent());
    }


}
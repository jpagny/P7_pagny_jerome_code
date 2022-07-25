package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTest {
    @Mock
    private CurvePointRepository curvePointRepository;

    @Test
    public void should_beReturnedCurvePoint_when_aNewCurvePointIsSaved() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        curvePoint = curvePointRepository.save(curvePoint);

        assertNotNull(curvePoint.getId());
        assertEquals(10, (int) curvePoint.getCurveId());
        assertNotNull(curvePoint.getCreationDate());
    }

    @Test
    public void should_beReturnedCurvePoint_when_aCurvePointIsUpdated() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setCurveId(20);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        curvePoint = curvePointRepository.save(curvePoint);

        assertNotNull(curvePoint.getId());
        assertEquals(20, (int) curvePoint.getCurveId());
        assertNotNull(curvePoint.getCreationDate());
    }

    @Test
    public void should_beReturnedAllCurvePoint_when_findAllIsCalled() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        List<CurvePoint> listCurvePoint = new ArrayList<>();
        listCurvePoint.add(curvePoint);
        when(curvePointRepository.findAll()).thenReturn(listCurvePoint);

        List<CurvePoint> listResult = curvePointRepository.findAll();

        assertTrue(listResult.size() > 0);
    }

    @Test
    public void should_beCurvePointNotPresent_when_curvePointIsDeleted() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        Integer id = curvePoint.getId();
        doNothing().when(curvePointRepository).delete(curvePoint);
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        curvePointRepository.delete(curvePoint);

        Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);

        assertFalse(curvePointList.isPresent());
    }


}
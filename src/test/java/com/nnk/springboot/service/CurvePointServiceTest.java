package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.implement.CurvePointService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointServiceTest {

    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Before
    public void initCurvePointService() {
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    public void should_beReturnedListCurvePoint_when_findAllIsCalled() {
        CurvePoint curvePoint1 = new CurvePoint(1, 10d, 30d);
        CurvePoint curvePoint2 = new CurvePoint(2, 10d, 30d);
        List<CurvePoint> listCurvePoint = new ArrayList<>();
        listCurvePoint.add(curvePoint1);
        listCurvePoint.add(curvePoint2);
        when(curvePointRepository.findAll()).thenReturn(listCurvePoint);

        List<CurvePoint> list = curvePointService.findAll();

        assertEquals(list.size(), 2);
    }


}

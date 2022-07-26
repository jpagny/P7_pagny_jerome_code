package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;
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
public class CurvePointEntityServiceTest {

    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Before
    public void initCurvePointService() {
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    public void should_beReturnedListCurvePoint_when_findAllIsCalled() {
        CurvePointEntity curvePointEntity1 = new CurvePointEntity(1, 10d, 30d);
        CurvePointEntity curvePointEntity2 = new CurvePointEntity(2, 10d, 30d);
        List<CurvePointEntity> listCurvePointEntity = new ArrayList<>();
        listCurvePointEntity.add(curvePointEntity1);
        listCurvePointEntity.add(curvePointEntity2);
        when(curvePointRepository.findAll()).thenReturn(listCurvePointEntity);

        List<CurvePointEntity> list = curvePointService.findAll();

        assertEquals(list.size(), 2);
    }


}

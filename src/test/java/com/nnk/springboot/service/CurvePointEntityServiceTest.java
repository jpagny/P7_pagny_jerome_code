package com.nnk.springboot.service;

import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.implement.CurvePointService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CurvePointEntityServiceTest {

    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeAll
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

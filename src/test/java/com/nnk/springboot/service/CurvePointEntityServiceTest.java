package com.nnk.springboot.service;

import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.implement.CurvePointService;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurvePointEntityServiceTest {

    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private ModelMapper modelMapper;


}

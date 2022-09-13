package com.nnk.springboot.service;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.entity.CurvePointEntity;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.CurvePointRepository;
import com.nnk.springboot.service.implement.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurvePointServiceTest {

    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    @DisplayName("Should be returned curvePoint when the curvePoint is found by id")
    public void should_beReturnedCurvePoint_when_theCurvePointIsFoundById() throws ResourceNotFoundException {
        CurvePointDTO curvePoint = new CurvePointDTO(1, 10d, 15d);

        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(new CurvePointEntity(1, 10d, 15d)));

        CurvePointDTO curvePointFound = curvePointService.findById(1);

        assertEquals(curvePointFound, curvePoint);
    }

    @Test
    @DisplayName("Should be exception when the curvePoint is not found by id")
    public void should_beException_when_theCurvePointIsNotFoundById() {
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                curvePointService.findById(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of curvePoint when get all curvePoints")
    public void should_beReturnedAListOfCurvePoint_when_getAllCurvePoints() {
        List<CurvePointDTO> listCurvePoints = new ArrayList<>();
        listCurvePoints.add(new CurvePointDTO(1, 10d, 3d));
        listCurvePoints.add(new CurvePointDTO(2, 2d, 150d));

        when(curvePointRepository.findAll()).thenReturn(listCurvePoints.stream()
                .map(curvePoint -> modelMapper.map(curvePoint, CurvePointEntity.class))
                .collect(Collectors.toList()));

        List<CurvePointDTO> listCurvePointsFound = curvePointService.findAll();

        assertEquals(listCurvePointsFound, listCurvePoints);
    }

    @Test
    @DisplayName("Should be returned curvePoint when a new curvePoint is created")
    public void should_BeReturnedNewCurvePoint_When_ANewCurvePointIsCreated() throws ResourceAlreadyExistException {
        CurvePointDTO curvePoint = new CurvePointDTO(1, 10d, 15d);

        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(new CurvePointEntity(1, 10d, 15d));

        CurvePointDTO newCurvePoint = curvePointService.create(curvePoint);

        assertEquals(newCurvePoint, curvePoint);
    }

    @Test
    @DisplayName("Should be returned curvePoint updated when a curvePoint is updated")
    public void should_beReturnedCurvePointUpdated_when_aCurvePointIsUpdated() throws ResourceNotFoundException {
        CurvePointDTO curvePointToUpdate = new CurvePointDTO(1, 10d, 15d);
        curvePointToUpdate.setTerm(105d);

        CurvePointEntity curvePointEntity = new CurvePointEntity(1, 105d, 15d);
        curvePointEntity.setTerm(105d);

        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePointEntity));
        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(curvePointEntity);

        CurvePointDTO curvePointUpdated = curvePointService.update(1, curvePointToUpdate);

        assertEquals(curvePointUpdated, curvePointToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the curvePoint to update doesnt exist")
    public void should_beException_when_theCurvePointToUpdateDoesntExist() {
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                curvePointService.update(1, curvePointDTO)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used curvePointRepository.delete method when a curvePoint will be deleted")
    public void should_beUsedCurvePointRepositoryDeleteMethod_when_aCurvePointWillBeDeleted() throws ResourceNotFoundException {

        CurvePointEntity curvePoint = new CurvePointEntity(1, 10d, 15d);

        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(curvePoint));
        doNothing().when(curvePointRepository).delete(any(CurvePointEntity.class));

        curvePointService.delete(1);

        verify(curvePointRepository, times(1)).delete(curvePoint);
    }

    @Test
    @DisplayName("Should be exception when the curvePoint to delete doesnt exist")
    public void should_beException_when_theCurvePointToDeleteDoesntExist() {
        when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                curvePointService.delete(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}

package com.nnk.springboot.service;


import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.RuleNameRepository;
import com.nnk.springboot.service.implement.RuleNameService;
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
public class RuleNameServiceTest {

    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        ruleNameService = new RuleNameService(ruleNameRepository);
    }

    @Test
    @DisplayName("Should be returned ruleName when the ruleName is found by id")
    public void should_beReturnedRuleName_when_theRuleNameIsFoundById() throws ResourceNotFoundException {
        RuleNameDTO ruleName = new RuleNameDTO("xxx", "xxx", "xxx", "xxx", "xxx", "xxx");

        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(new RuleNameEntity("xxx", "xxx", "xxx", "xxx", "xxx", "xxx")));

        RuleNameDTO ruleNameFound = ruleNameService.findById(1);

        assertEquals(ruleNameFound, ruleName);
    }

    @Test
    @DisplayName("Should be exception when the ruleName is not found by id")
    public void should_beException_when_theRuleNameIsNotFoundById() {
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ruleNameService.findById(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of ruleName when get all ruleNames")
    public void should_beReturnedAListOfRuleName_when_getAllRuleNames() {
        List<RuleNameDTO> listRuleNames = new ArrayList<>();
        listRuleNames.add(new RuleNameDTO("xxx", "xxx", "xxx", "xxx", "xxx", "xxx"));
        listRuleNames.add(new RuleNameDTO("ccc", "ccc", "ccc", "ccc", "ccc", "ccc"));

        when(ruleNameRepository.findAll()).thenReturn(listRuleNames.stream()
                .map(ruleName -> modelMapper.map(ruleName, RuleNameEntity.class))
                .collect(Collectors.toList()));

        List<RuleNameDTO> listRuleNameFound = ruleNameService.findAll();

        assertEquals(listRuleNameFound, listRuleNames);
    }

    @Test
    @DisplayName("Should be returned ruleName when a new ruleName is created")
    public void should_BeReturnedNewRuleName_When_ANewRuleNameIsCreated() throws ResourceAlreadyExistException {
        RuleNameDTO ruleName = new RuleNameDTO("xxx", "xxx", "xxx", "xxx", "xxx", "xxx");

        when(ruleNameRepository.save(any(RuleNameEntity.class))).thenReturn(new RuleNameEntity("xxx", "xxx", "xxx", "xxx", "xxx", "xxx"));

        RuleNameDTO newRuleName = ruleNameService.create(ruleName);

        assertEquals(newRuleName, ruleName);
    }

    @Test
    @DisplayName("Should be returned ruleName updated when a ruleName is updated")
    public void should_beReturnedRuleNameUpdated_when_aRuleNameIsUpdated() throws ResourceNotFoundException {
        RuleNameDTO ruleNameToUpdate = new RuleNameDTO("xxx", "xxx", "xxx", "xxx", "xxx", "xxx");
        ruleNameToUpdate.setDescription("ccc");

        RuleNameEntity RuleNameEntity = new RuleNameEntity("xxx", "ccc", "xxx", "xxx", "xxx", "xxx");


        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(RuleNameEntity));
        when(ruleNameRepository.save(any(RuleNameEntity.class))).thenReturn(RuleNameEntity);

        RuleNameDTO ruleNameUpdated = ruleNameService.update(1, ruleNameToUpdate);

        assertEquals(ruleNameUpdated, ruleNameToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the ruleName to update doesnt exist")
    public void should_beException_when_theRuleNameToUpdateDoesntExist() {
        RuleNameDTO RuleNameDTO = new RuleNameDTO();
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ruleNameService.update(1, RuleNameDTO)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used RuleNameRepository.delete method when a ruleName will be deleted")
    public void should_beUsedRuleNameRepositoryDeleteMethod_when_aRuleNameWillBeDeleted() throws ResourceNotFoundException {

        RuleNameEntity ruleName = new RuleNameEntity("xxx", "xxx", "xxx", "xxx", "xxx", "xxx");

        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.of(ruleName));
        doNothing().when(ruleNameRepository).delete(any(RuleNameEntity.class));

        ruleNameService.delete(1);

        verify(ruleNameRepository, times(1)).delete(ruleName);
    }

    @Test
    @DisplayName("Should be exception when the ruleName to delete doesnt exist")
    public void should_beException_when_theRuleNameToDeleteDoesntExist() {
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ruleNameService.delete(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}

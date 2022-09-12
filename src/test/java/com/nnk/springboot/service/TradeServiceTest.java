package com.nnk.springboot.service;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.TradeRepository;
import com.nnk.springboot.service.implement.TradeService;
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
public class TradeServiceTest {

    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        tradeService = new TradeService(tradeRepository);
    }

    @Test
    @DisplayName("Should be returned trade when the trade is found by id")
    public void should_beReturnedTrade_when_theTradeIsFoundById() throws ResourceNotFoundException {
        TradeDTO trade = new TradeDTO("xxx", "ccc", 15d);

        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(new TradeEntity("xxx", "ccc", 15d)));

        TradeDTO tradeFound = tradeService.findById(1);

        assertEquals(tradeFound, trade);
    }

    @Test
    @DisplayName("Should be exception when the trade is not found by id")
    public void should_beException_when_theTradeIsNotFoundById() {
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                tradeService.findById(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of trade when get all trades")
    public void should_beReturnedAListOfTrade_when_getAllTrades() {
        List<TradeDTO> listTrade = new ArrayList<>();
        listTrade.add(new TradeDTO("xxx", "ccc", 15d));
        listTrade.add(new TradeDTO("aaa", "ccc", 10d));

        when(tradeRepository.findAll()).thenReturn(listTrade.stream()
                .map(trade -> modelMapper.map(trade, TradeEntity.class))
                .collect(Collectors.toList()));

        List<TradeDTO> listTradeFound = tradeService.findAll();

        assertEquals(listTradeFound, listTrade);
    }

    @Test
    @DisplayName("Should be returned trade when a new trade is created")
    public void should_BeReturnedNewTrade_When_ANewTradeIsCreated() {
        TradeDTO trade = new TradeDTO("xxx", "ccc", 15d);

        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(new TradeEntity("xxx", "ccc", 15d));

        TradeDTO newTrade = tradeService.create(trade);

        assertEquals(newTrade, trade);
    }

    @Test
    @DisplayName("Should be returned trade updated when a trade is updated")
    public void should_beReturnedTradeUpdated_when_aTradeIsUpdated() throws ResourceNotFoundException {
        TradeDTO tradeToUpdate = new TradeDTO("xxx", "ccc", 15d);
        tradeToUpdate.setType("bbb");

        TradeEntity TradeEntity = new TradeEntity("xxx", "bbb", 15d);

        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(TradeEntity));
        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(TradeEntity);

        TradeDTO tradeUpdated = tradeService.update(1, tradeToUpdate);

        assertEquals(tradeUpdated, tradeToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the trade to update doesnt exist")
    public void should_beException_when_theTradeToUpdateDoesntExist() {
        TradeDTO TradeDTO = new TradeDTO();
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                tradeService.update(1, TradeDTO)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used tradeRepository.delete method when a trade will be deleted")
    public void should_beUsedTradeRepositoryDeleteMethod_when_aTradeWillBeDeleted() throws ResourceNotFoundException {

        TradeEntity trade = new TradeEntity("xxx", "ccc", 15d);

        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).delete(any(TradeEntity.class));

        tradeService.delete(1);

        verify(tradeRepository, times(1)).delete(trade);
    }

    @Test
    @DisplayName("Should be exception when the trade to delete doesnt exist")
    public void should_beException_when_theTradeToDeleteDoesntExist() {
        when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                tradeService.delete(1)
        );

        String expectedMessage = "Resource doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}

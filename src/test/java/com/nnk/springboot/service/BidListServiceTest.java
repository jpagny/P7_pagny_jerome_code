package com.nnk.springboot.service;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.implement.BidListService;
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
public class BidListServiceTest {

    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        ModelMapper modelMapper = new ModelMapper();
        bidListService = new BidListService(bidListRepository, modelMapper);
    }

    @Test
    @DisplayName("Should be returned bidList when the bidList is found by id")
    public void should_beReturnedBidList_when_theBidListIsFoundById() throws ResourceNotFoundException {
        BidListDTO bidListDTO = new BidListDTO("1", "xxx", 100d);

        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(new BidListEntity("1", "xxx", 100d)));

        BidListDTO bidListFound = bidListService.findById(1);

        assertEquals(bidListFound, bidListDTO);
    }

    @Test
    @DisplayName("Should be exception when the bidList is not found by id")
    public void should_beException_when_theBidListIsNotFoundById() {
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                bidListService.findById(1)
        );

        String expectedMessage = "Bid list doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of bidList when get all bidLists")
    public void should_beReturnedAListOfBidList_when_getAllBidList() {
        List<BidListDTO> listBidList = new ArrayList<>();
        listBidList.add(new BidListDTO("1", "xxx", 100d));
        listBidList.add(new BidListDTO("2", "xxx", 20d));

        when(bidListRepository.findAll()).thenReturn(listBidList.stream()
                .map(bidList -> modelMapper.map(bidList, BidListEntity.class))
                .collect(Collectors.toList()));

        List<BidListDTO> listBidListFound = bidListService.findAll();

        assertEquals(listBidListFound, listBidList);
    }

    @Test
    @DisplayName("Should be returned bidList when a new bidList is created")
    public void should_BeReturnedNewBidList_When_ANewBidListIsCreated() {
        BidListDTO bidListDTO = new BidListDTO("1", "xxx", 100d);

        when(bidListRepository.save(any(BidListEntity.class))).thenReturn(new BidListEntity("1", "xxx", 100d));

        BidListDTO newBidList = bidListService.create(bidListDTO);

        assertEquals(newBidList, bidListDTO);
    }

    @Test
    @DisplayName("Should be returned bidList updated when a bidList is updated")
    public void should_beReturnedBidListUpdated_when_aBidListIsUpdated() throws ResourceNotFoundException {
        BidListDTO bidListToUpdate = new BidListDTO("1", "xxx", 100d);
        bidListToUpdate.setBidQuantity(10d);

        BidListEntity bidListEntity = new BidListEntity("1", "xxx", 10d);

        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidListEntity));
        when(bidListRepository.save(any(BidListEntity.class))).thenReturn(bidListEntity);

        BidListDTO bidListUpdated = bidListService.update(1, bidListToUpdate);

        assertEquals(bidListUpdated, bidListToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the bidList to update doesnt exist")
    public void should_beException_when_theBidListToUpdateDoesntExist() {
        BidListDTO bidListDTO = new BidListDTO();
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                bidListService.update(1, bidListDTO)
        );

        String expectedMessage = "Bid list doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used bidListRepository.delete method when a bidList will be deleted")
    public void should_beUsedBidListRepositoryDeleteMethod_when_aBidListWillBeDeleted() throws ResourceNotFoundException {

        BidListEntity bidList = new BidListEntity("1", "xxx", 10d);

        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.of(bidList));
        doNothing().when(bidListRepository).delete(any(BidListEntity.class));

        bidListService.delete(1);

        verify(bidListRepository, times(1)).delete(bidList);
    }

    @Test
    @DisplayName("Should be exception when the bidList to delete doesnt exist")
    public void should_beException_when_theBidListToDeleteDoesntExist() {
        when(bidListRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                bidListService.delete(1)
        );

        String expectedMessage = "Bid list doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

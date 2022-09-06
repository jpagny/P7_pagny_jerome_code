package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.implement.RatingService;
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
public class RatingServiceTest {

    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void initService() {
        ModelMapper modelMapper = new ModelMapper();
        ratingService = new RatingService(ratingRepository, modelMapper);
    }

    @Test
    @DisplayName("Should be returned rating when the rating is found by id")
    public void should_beReturnedRating_when_theRatingIsFoundById() throws ResourceNotFoundException {
        RatingDTO rating = new RatingDTO("xxx", "xxx", "xxx", 10);

        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(new RatingEntity("xxx", "xxx", "xxx", 10)));

        RatingDTO ratingFound = ratingService.findById(1);

        assertEquals(ratingFound, rating);
    }

    @Test
    @DisplayName("Should be exception when the rating is not found by id")
    public void should_beException_when_theRatingIsNotFoundById() {
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.findById(1)
        );

        String expectedMessage = "Rating doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be returned a list of rating when get all ratings")
    public void should_beReturnedAListOfRating_when_getAllRatings() {
        List<RatingDTO> listRating = new ArrayList<>();
        listRating.add(new RatingDTO("xxx", "xxx", "xxx", 10));
        listRating.add(new RatingDTO("aaa", "bbb", "ccc", 30));

        when(ratingRepository.findAll()).thenReturn(listRating.stream()
                .map(rating -> modelMapper.map(rating, RatingEntity.class))
                .collect(Collectors.toList()));

        List<RatingDTO> listRatingFound = ratingService.findAll();

        assertEquals(listRatingFound, listRating);
    }

    @Test
    @DisplayName("Should be returned rating when a new rating is created")
    public void should_BeReturnedNewRating_When_ANewRatingIsCreated() {
        RatingDTO rating = new RatingDTO("xxx", "xxx", "xxx", 10);

        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(new RatingEntity("xxx", "xxx", "xxx", 10));

        RatingDTO newRating = ratingService.create(rating);

        assertEquals(newRating, rating);
    }

    @Test
    @DisplayName("Should be returned rating updated when a rating is updated")
    public void should_beReturnedRatingUpdated_when_aRatingIsUpdated() throws ResourceNotFoundException {
        RatingDTO ratingToUpdate = new RatingDTO("xxx", "xxx", "xxx", 10);
        ratingToUpdate.setFitchRating("ccc");

        RatingEntity ratingEntity = new RatingEntity("xxx", "xxx", "ccc", 10);

        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(ratingEntity));
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(ratingEntity);

        RatingDTO ratingUpdated = ratingService.update(1, ratingToUpdate);

        assertEquals(ratingUpdated, ratingToUpdate);
    }

    @Test
    @DisplayName("Should be exception when the rating to update doesnt exist")
    public void should_beException_when_theRatingToUpdateDoesntExist() {
        RatingDTO RatingDTO = new RatingDTO();
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.update(1, RatingDTO)
        );

        String expectedMessage = "Rating doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be used RatingRepository.delete method when a rating will be deleted")
    public void should_beUsedRatingRepositoryDeleteMethod_when_aRatingWillBeDeleted() throws ResourceNotFoundException {

        RatingEntity rating = new RatingEntity("xxx", "xxx", "ccc", 10);

        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(rating));
        doNothing().when(ratingRepository).delete(any(RatingEntity.class));

        ratingService.delete(1);

        verify(ratingRepository, times(1)).delete(rating);
    }

    @Test
    @DisplayName("Should be exception when the rating to delete doesnt exist")
    public void should_beException_when_theRatingToDeleteDoesntExist() {
        when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                ratingService.delete(1)
        );

        String expectedMessage = "Rating doesn't exist with id : 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}

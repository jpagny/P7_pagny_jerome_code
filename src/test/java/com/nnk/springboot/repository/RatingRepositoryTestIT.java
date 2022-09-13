package com.nnk.springboot.repository;


import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.repository.impl.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RatingRepositoryTestIT {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void ratingTest() {
        RatingEntity rating = new RatingEntity("Moodys Rating", "Sand PRating", "Fitch Rating", 10);


        // Save
        rating = ratingRepository.save(rating);
        assertNotNull(rating.getId());
        assertEquals(10, (int) rating.getOrderNumber());

        // Update
        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        assertEquals(20, (int) rating.getOrderNumber());

        // Find
        List<RatingEntity> listResult = ratingRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<RatingEntity> theRating = ratingRepository.findById(id);
        assertFalse(theRating.isPresent());
    }


}
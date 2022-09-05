package com.nnk.springboot.repository;


import com.nnk.springboot.entity.RatingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RatingRepositoryTest {

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
        Optional<RatingEntity> ratingList = ratingRepository.findById(id);
        assertFalse(ratingList.isPresent());
    }


}
package com.nnk.springboot.repository;


import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.repository.impl.BidListRepository;
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
public class BidListRepositoryTestIT {


    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {
        BidListEntity bid = new BidListEntity("Account Test", "Type Test", 10d);

        // Save
        bid = bidListRepository.save(bid);
        assertNotNull(bid.getId());
        assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidListEntity> listResult = bidListRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getId();
        bidListRepository.delete(bid);
        Optional<BidListEntity> theBidList = bidListRepository.findById(id);
        assertFalse(theBidList.isPresent());
    }


}
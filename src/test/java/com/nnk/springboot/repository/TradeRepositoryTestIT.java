package com.nnk.springboot.repository;

import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.repository.impl.TradeRepository;
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
public class TradeRepositoryTestIT {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void tradeTest() {
        TradeEntity trade = new TradeEntity("Trade Account", "Type", 10d);

        // Save
        trade = tradeRepository.save(trade);
        assertNotNull(trade.getId());
        assertEquals("Trade Account", trade.getAccount());

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertEquals("Trade Account Update", trade.getAccount());

        // Find
        List<TradeEntity> listResult = tradeRepository.findAll();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getId();
        tradeRepository.delete(trade);
        Optional<TradeEntity> theTrade = tradeRepository.findById(id);
        assertFalse(theTrade.isPresent());
    }


}
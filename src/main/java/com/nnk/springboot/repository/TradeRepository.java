package com.nnk.springboot.repository;


import com.nnk.springboot.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}



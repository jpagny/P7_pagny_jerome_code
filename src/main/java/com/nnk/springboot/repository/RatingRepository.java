package com.nnk.springboot.repository;


import com.nnk.springboot.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

}

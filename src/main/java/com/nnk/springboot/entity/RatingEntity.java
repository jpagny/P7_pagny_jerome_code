package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Table(name = "rating")
@Entity
@Data
@AllArgsConstructor
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "moodysRating")
    private String moodysRating;

    @Column(name = "sandPRating")
    private String sandPRating;

    @Column(name = "fitchRating")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;


    public RatingEntity() {

    }

    public RatingEntity(String theMoodysRating, String theSandPRating, String theFitchRating, Integer theOrderNumber) {
        this.moodysRating = theMoodysRating;
        this.sandPRating = theSandPRating;
        this.orderNumber = theOrderNumber;
    }

}

package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank
    private String moodysRating;

    @Column(name = "sandPRating")
    @NotBlank
    private String sandPRating;

    @Column(name = "fitchRating")
    @NotBlank
    private String fitchRating;

    @Column(name = "orderNumber")
    @NotNull
    private Integer orderNumber;


    public RatingEntity() {

    }

    public RatingEntity(String theMoodysRating, String theSandPRating, String theFitchRating, Integer theOrderNumber) {
        this.moodysRating = theMoodysRating;
        this.sandPRating = theSandPRating;
        this.fitchRating = theFitchRating;
        this.orderNumber = theOrderNumber;
    }

}

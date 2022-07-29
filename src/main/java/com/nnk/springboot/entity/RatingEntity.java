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
}

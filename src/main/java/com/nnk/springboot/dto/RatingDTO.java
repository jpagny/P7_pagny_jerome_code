package com.nnk.springboot.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingDTO {

    private Integer id;
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;

    @NotNull(message = "Order is mandatory")
    private Integer orderNumber;
}

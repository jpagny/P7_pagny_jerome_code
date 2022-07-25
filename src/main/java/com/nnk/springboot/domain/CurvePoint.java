package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@AllArgsConstructor
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Length(min = 3, max = 20)
    private Integer curveId;

    @NotNull
    private Timestamp asOfDate;

    @NotNull
    @NotBlank
    private Double term;

    @NotNull
    @NotBlank
    private Double value;

    @NotNull
    private Timestamp creationDate;

    public CurvePoint() {

    }

}

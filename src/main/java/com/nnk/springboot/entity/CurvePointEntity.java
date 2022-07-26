package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Table(name = "curvepoint")
@Getter
@Setter
@AllArgsConstructor
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Length(min = 3, max = 20)
    private Integer curveId;

    private Timestamp asOfDate;

    @NotNull
    @NotBlank
    private Double term;

    @NotNull
    @NotBlank
    private Double value;

    @NotNull
    private Timestamp creationDate;

    public CurvePointEntity() {

    }
    public CurvePointEntity(int theCurveId, Double theTerm, Double theValue) {

        id = theCurveId;

        curveId = theCurveId;
        term = theTerm;
        value = theValue;

        creationDate = Timestamp.from(Instant.now());
    }


}

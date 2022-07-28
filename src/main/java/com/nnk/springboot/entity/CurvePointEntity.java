package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    private Integer curveId;

    private Timestamp asOfDate;

    private Double term;

    private Double value;

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

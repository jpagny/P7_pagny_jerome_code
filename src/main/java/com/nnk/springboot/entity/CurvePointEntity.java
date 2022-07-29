package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "curvePoint")
@Data
@AllArgsConstructor
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "curveId")
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @CreationTimestamp
    @Column(name = "creationDate")
    private Timestamp creationDate;

    public CurvePointEntity() {

    }

    public CurvePointEntity(int theCurveId, Double theTerm, Double theValue) {
        curveId = theCurveId;
        term = theTerm;
        value = theValue;
    }


}

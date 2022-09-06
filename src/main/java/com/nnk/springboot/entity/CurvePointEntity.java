package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Integer curveId;

    @Column(name = "asOfDate")
    private Timestamp asOfDate;

    @Column(name = "term")
    @NotNull
    private Double term;

    @Column(name = "value")
    @NotNull
    private Double value;

    @CreationTimestamp
    @Column(name = "creationDate")
    private Timestamp creationDate;

    public CurvePointEntity() {

    }
    public CurvePointEntity(Double theTerm, Double theValue) {
        term = theTerm;
        value = theValue;
    }


}

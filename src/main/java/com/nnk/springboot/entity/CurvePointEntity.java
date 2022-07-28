package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "curvePoint")
@Data
@AllArgsConstructor
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(0)
    private Integer curveId;

    @CreationTimestamp
    private Timestamp asOfDate;

    @NotNull
    @Min(value = 0, message = "Term must be positive")
    private Double term;

    @NotNull
    @Min(value = 0, message = "Value must be positive")
    private Double value;

    @CreationTimestamp
    private Timestamp creationDate;

    public CurvePointEntity() {

    }

    public CurvePointEntity(int theCurveId, Double theTerm, Double theValue) {
        curveId = theCurveId;
        term = theTerm;
        value = theValue;
    }


}

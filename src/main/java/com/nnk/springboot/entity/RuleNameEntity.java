package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "ruleName")
@Entity
@Data
@AllArgsConstructor
public class RuleNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "json")
    @NotBlank
    private String json;

    @Column(name = "template")
    @NotBlank
    private String template;

    @Column(name = "sqlStr")
    @NotBlank
    private String sqlStr;

    @Column(name = "sqlPart")
    @NotBlank
    private String sqlPart;


    public RuleNameEntity() {

    }

    public RuleNameEntity(String theName, String theDescription, String theJson, String theTemplate, String theSqlStr, String theStringPart) {
        this.name = theName;
        this.description = theDescription;
        this.json = theJson;
        this.template = theTemplate;
        this.sqlStr = theSqlStr;
        this.sqlPart = theStringPart;
    }

}

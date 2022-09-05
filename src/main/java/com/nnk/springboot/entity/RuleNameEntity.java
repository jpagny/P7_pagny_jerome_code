package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

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
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "json")
    private String json;

    @Column(name = "template")
    private String template;

    @Column(name = "sqlStr")
    private String sqlStr;

    @Column(name = "sqlPart")
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

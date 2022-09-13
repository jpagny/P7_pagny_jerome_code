package com.nnk.springboot.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class RuleNameDTO {

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "json is mandatory")
    private String json;

    @NotBlank(message = "template is mandatory")
    private String template;

    @NotBlank(message = "sql is mandatory")
    private String sqlStr;

    @NotBlank(message = "sqlPart is mandatory")
    private String sqlPart;

    public RuleNameDTO(String theName, String theDescription, String theJson, String theTemplate, String theSqlStr, String theStringPart) {
        this.name = theName;
        this.description = theDescription;
        this.json = theJson;
        this.template = theTemplate;
        this.sqlStr = theSqlStr;
        this.sqlPart = theStringPart;
    }


}

package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "fullname")
    @NotBlank
    private String fullname;

    @Column(name = "role")
    @NotBlank
    private String role;

    public UserEntity() {

    }

    public UserEntity(String theFullName, String theUsername, String thePassword, String theRole) {
        this.fullname = theFullName;
        this.username = theUsername;
        this.password = thePassword;
        this.role = theRole;
    }


}

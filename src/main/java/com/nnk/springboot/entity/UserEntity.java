package com.nnk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "role")
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

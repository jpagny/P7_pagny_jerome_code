package com.nnk.springboot.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class UserDTO {

    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Full Name is mandatory")
    private String fullname;

    @NotNull(message = "Role is mandatory")
    private String role;

    public UserDTO(String theFullName, String theUsername, String thePassword, String theRole) {
        this.fullname = theFullName;
        this.username = theUsername;
        this.password = thePassword;
        this.role = theRole;
    }
}

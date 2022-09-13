package com.nnk.springboot.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN(),
    USER();

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

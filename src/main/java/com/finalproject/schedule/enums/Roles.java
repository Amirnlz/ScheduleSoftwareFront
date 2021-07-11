package com.finalproject.schedule.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

    ADMIN,
    MASTER,
    STUDENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

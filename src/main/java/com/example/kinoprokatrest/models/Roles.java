package com.example.kinoprokatrest.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    admin, user;

    @Override
    public String getAuthority() {
        return name();
    }
}

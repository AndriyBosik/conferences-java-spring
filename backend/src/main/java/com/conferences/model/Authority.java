package com.conferences.model;

import com.conferences.entity.Role;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private final Role role;

    public Authority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getTitle().toUpperCase();
    }
}

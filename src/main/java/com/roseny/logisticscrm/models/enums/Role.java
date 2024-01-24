package com.roseny.logisticscrm.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_DEFAULT, ROLE_SUPPORT, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

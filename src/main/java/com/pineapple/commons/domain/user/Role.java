package com.pineapple.commons.domain.user;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Role {
    ADMIN,
    USER;

    public static Role getByName(final String name) {
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equalsIgnoreCase(name.substring(5)))
                .findFirst()
                .orElse(null);
    }

    public static Role get(final String name) {
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

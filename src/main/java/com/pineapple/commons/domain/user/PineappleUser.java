package com.pineapple.commons.domain.user;


import com.pineapple.commons.exception.UserAuthenticationException;
import lombok.Getter;
import lombok.Setter;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PineappleUser implements Principal {

    private String name;
    private String email;

    private List<Authority> authorities = new LinkedList<>();

    public PineappleUser(String name, String email, Authority authority) {
        this.name = name;
        this.email = email;
        this.authorities.add(authority);
    }

    public PineappleUser(String name, String email, List<Authority> authorities) {
        this.name = name;
        this.email = email;
        this.authorities.addAll(authorities);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getRole() {
        return authorities
                .stream()
                .map(Authority::getRole)
                .filter(Objects::nonNull)
                .sorted()
                    .map(Role::name)
                .findFirst()
                    .orElseThrow(() -> new UserAuthenticationException("Access denied"));
    }
}

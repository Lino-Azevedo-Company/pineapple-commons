package com.pineapple.commons.infra.security.authentication;

import com.pineapple.commons.domain.user.PineappleUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

@Getter
@Setter
public class AuthenticationToken extends JwtAuthenticationToken {

    private PineappleUser user;

    public AuthenticationToken(PineappleUser user, Collection<? extends GrantedAuthority> authorities, Jwt jwt) {
        super(jwt, authorities);
        this.setAuthenticated(true);
        this.setUser(user);
    }
}

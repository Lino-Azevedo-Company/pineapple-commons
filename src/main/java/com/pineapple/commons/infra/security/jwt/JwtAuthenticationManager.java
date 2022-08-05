package com.pineapple.commons.infra.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.pineapple.commons.domain.user.PineappleUser;
import com.pineapple.commons.infra.security.authorization.PineappleGrantedAuthority;
import com.pineapple.commons.domain.user.Authority;
import com.pineapple.commons.domain.user.Role;
import com.pineapple.commons.exception.JwtTokenException;
import com.pineapple.commons.infra.security.authentication.AuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private final List<Consumer<JWT>> jwtVerifiers;
    private final JwtAuthenticationTokenAdapter authenticationTokenAdapter;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getPrincipal();
        JWT jwt;
        JWTClaimsSet claims;

        try {
            jwt = JWTParser.parse(token);
            claims = jwt.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new JwtTokenException("Token cannot be extracted!");
        }

        verify(jwt);

        final String role = (String) claims.getClaim("role");
        final Authority authority = getAuthority(role);
        return new AuthenticationToken(
                getUser(claims, authority),
                getGrantedAuthorities(authority),
                authenticationTokenAdapter.adapt(token, jwt));
    }

    private void verify(JWT jwt) {
        for (var verifier : jwtVerifiers) {
            verifier.accept(jwt);
        }
    }

    private Authority getAuthority(final String roles) {
        return new Authority(Role.get(roles));
    }

    private PineappleUser getUser(final JWTClaimsSet claims, final Authority authority) {
        var name = (String) claims.getClaim("name");
        var email = (String) claims.getClaim("email");

        return new PineappleUser(name, email, authority);
    }

    private List<GrantedAuthority> getGrantedAuthorities(final Authority authority) {
        return new LinkedList<>() {{
            add(new PineappleGrantedAuthority(authority));
        }};
    }
}

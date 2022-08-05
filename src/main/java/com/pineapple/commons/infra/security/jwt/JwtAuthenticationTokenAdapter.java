package com.pineapple.commons.infra.security.jwt;

import com.nimbusds.jwt.JWT;
import com.pineapple.commons.exception.JwtTokenException;
import com.pineapple.commons.infra.security.authentication.AuthenticationTokenAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.LinkedHashMap;

@Component
public class JwtAuthenticationTokenAdapter implements AuthenticationTokenAdapter<JWT, Jwt> {

    private static final String EXPIRATION_TIME = "exp";
    private static final String ISSUE_TIME = "iat";

    @Override
    public Jwt adapt(String token, JWT parsedJwt) {
        try {
            var headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
            var claims = new LinkedHashMap<>(parsedJwt.getJWTClaimsSet().getClaims());

            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(headers))
                    .claims(c -> c.putAll(claims))
                        .claim(EXPIRATION_TIME,
                                parsedJwt.getJWTClaimsSet()
                                        .getExpirationTime()
                                        .toInstant())  // type convert is necessary
                        .claim(ISSUE_TIME,
                                parsedJwt.getJWTClaimsSet()
                                        .getIssueTime()
                                        .toInstant())  // type convert is necessary
                    .build();

        } catch (ParseException e) {
            throw new JwtTokenException("error adapting token", e);
        }
    }
}

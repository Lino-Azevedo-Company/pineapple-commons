package com.pineapple.commons.infra.security.jwt.verifiers;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.pineapple.commons.exception.JwtTokenException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Consumer;

@Component
public class NotBeforeTimeClaimsVerifier implements Consumer<JWT> {

    @Override
    public void accept(JWT jwt) {
        try {
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            Date notBefore = claims.getNotBeforeTime();
            if (notBefore == null || notBefore.after(new Date())) {
                throw new JwtTokenException("Not before is after the current date");
            }
        } catch (ParseException e) {
            throw new JwtTokenException("Invalid token");
        }
    }
}

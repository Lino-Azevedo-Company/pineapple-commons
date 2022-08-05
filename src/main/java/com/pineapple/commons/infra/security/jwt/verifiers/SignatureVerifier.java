package com.pineapple.commons.infra.security.jwt.verifiers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
import com.pineapple.commons.exception.JwtTokenException;
import com.pineapple.commons.infra.config.ConfigurationRetriever;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SignatureVerifier implements Consumer<JWT> {

    private final JWSVerifier verifier;

    public SignatureVerifier(ConfigurationRetriever retriever) {
        try {
            this.verifier = new MACVerifier(retriever.getEnv("JWT_SECRET"));
        } catch (JOSEException e) {
            throw new IllegalArgumentException("The secret is invalid", e);
        }
    }

    @Override
    public void accept(JWT jwt) {
        var signedJWT = (SignedJWT) jwt;
        try {
            if (!signedJWT.verify(verifier)) {
                throw new JwtTokenException("Invalid signature");
            }
        } catch (JOSEException e) {
            throw new JwtTokenException("Signature not verified");
        }
    }
}

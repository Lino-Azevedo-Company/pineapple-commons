package com.pineapple.commons.infra.security.authentication;

public interface AuthenticationTokenAdapter<A, T> {

    T adapt(final String token, final A toAdapt);
}

package com.pineapple.commons.infra.security.authorization;

import com.pineapple.commons.domain.user.Authority;
import com.pineapple.commons.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@ToString
public class PineappleGrantedAuthority implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return "ROLE_" + authority.getRoleName();
    }

    public Role getRole() {
        return authority.getRole();
    }
}

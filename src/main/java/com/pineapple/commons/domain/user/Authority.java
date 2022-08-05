package com.pineapple.commons.domain.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "pna_authority")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "role")
public class Authority {

//    @Id
//    @Column(name = "authority_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "role", nullable = false)
//    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getRoleName() {
        return role.name();
    }

    @Override
    public String toString() {
        return role.name();
    }

    public Authority(Role role) {
        this.role = role;
    }
}

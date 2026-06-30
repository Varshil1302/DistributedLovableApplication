package com.example.disributed_lovable.CommonLib.common_lib.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JwtUserPrincipal(
        Long userId,
        String email,
        List<GrantedAuthority> authorityList
) {
}

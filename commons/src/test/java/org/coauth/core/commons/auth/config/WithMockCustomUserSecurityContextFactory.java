package org.coauth.core.commons.auth.config;

import org.coauth.core.domain.auth.dto.Role;
import org.coauth.core.domain.auth.dto.UserDto;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    UserDto principal =
        UserDto.builder()
            .username(customUser.username())
            .password("123")
            .roles(List.of(Role.ROLE_ADMIN))
            .build();
    Authentication auth =
        UsernamePasswordAuthenticationToken.authenticated(
            principal, "password", principal.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}

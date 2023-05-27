package org.coauth.core.commons.auth.config;

import org.coauth.core.domain.auth.dto.UserDto;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FetchPrincipalComponent {

  public Mono<UserDto> getAuthDetails() {
    return ReactiveSecurityContextHolder.getContext()
        .switchIfEmpty(Mono.error(new AuthorizationServiceException("Invalid Login")))
        .flatMap(
            securityContext ->
                Mono.just((UserDto) securityContext.getAuthentication().getPrincipal()));
  }
}

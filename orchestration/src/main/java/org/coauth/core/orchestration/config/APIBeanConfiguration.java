package org.coauth.core.orchestration.config;

import org.coauth.core.domain.auth.ports.api.AuthServiceAPI;
import org.coauth.core.domain.auth.usecase.UserService;
import org.coauth.core.domain.totp.ports.api.TOtpUserServiceAPI;
import org.coauth.core.domain.totp.usecase.TOtpUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIBeanConfiguration {

  private UserService userService;

  private TOtpUserUseCase tOtpUserUseCase;

  public APIBeanConfiguration(UserService userService, TOtpUserUseCase tOtpUserUseCase) {
    this.userService = userService;
    this.tOtpUserUseCase = tOtpUserUseCase;
  }

  @Bean
  AuthServiceAPI authServiceAPI() {
    return userService;
  }

  @Bean
  TOtpUserServiceAPI tOtpUserServiceAPI() {
    return tOtpUserUseCase;
  }
}

package org.coauth.core.orchestration.config;

import org.coauth.core.domain.auth.ports.spi.JWTUtilSPI;
import org.coauth.core.domain.security.ports.spi.CryptoAlgorithmsSPI;
import org.coauth.core.domain.totp.ports.spi.TOtpCryptoSPI;
import org.coauth.core.infrastructure.crypto.adapters.CryptoAlgorithmsAdapter;
import org.coauth.core.infrastructure.crypto.adapters.JWTUtilAdapter;
import org.coauth.core.infrastructure.crypto.adapters.TOtpCryptoAdapter;
import org.coauth.core.infrastructure.crypto.service.JWTUtilService;
import org.coauth.core.infrastructure.crypto.service.TOtpSecretEncryption;
import org.coauth.core.infrastructure.crypto.service.TOtpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SPICryptoBeanConfiguration {

  private JWTUtilService jwtUtilService;

  private TOtpService tOtpService;

  private TOtpSecretEncryption tOtpSecretEncryption;

  public SPICryptoBeanConfiguration(
      JWTUtilService jwtUtilService,
      TOtpService tOtpService,
      TOtpSecretEncryption tOtpSecretEncryption) {
    this.jwtUtilService = jwtUtilService;
    this.tOtpService = tOtpService;
    this.tOtpSecretEncryption = tOtpSecretEncryption;
  }

  @Bean
  public JWTUtilSPI jwtUtilspi() {
    return new JWTUtilAdapter(jwtUtilService);
  }

  @Bean
  public CryptoAlgorithmsSPI cryptoAlgorithmsSPI() {
    return new CryptoAlgorithmsAdapter();
  }

  @Bean
  public TOtpCryptoSPI tOtpCryptoSPI() {
    return new TOtpCryptoAdapter(tOtpService, tOtpSecretEncryption);
  }
}

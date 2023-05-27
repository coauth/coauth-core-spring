package org.coauth.core.orchestration.config;

import org.coauth.core.domain.auth.ports.spi.SystemMasterSPI;
import org.coauth.core.domain.auth.ports.spi.SystemUserMasterSPI;
import org.coauth.core.domain.totp.ports.spi.TOtpUserMasterSPI;
import org.coauth.core.infrastructure.coredb.auth.adapters.SystemMasterAdapter;
import org.coauth.core.infrastructure.coredb.auth.adapters.SystemUserMasterAdapter;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemMasterRepository;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemUserMasterRepository;
import org.coauth.core.infrastructure.coredb.totp.adapters.TOtpUserMasterAdapter;
import org.coauth.core.infrastructure.coredb.totp.repository.TOtpUserMasterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SPICoreDbBeanConfiguration {

  private SystemMasterRepository systemMasterRepository;

  private SystemUserMasterRepository systemUserMasterRepository;

  private TOtpUserMasterRepository tOtpUserMasterRepository;

  public SPICoreDbBeanConfiguration(
      SystemUserMasterRepository systemUserMasterRepository,
      SystemMasterRepository systemMasterRepository,
      TOtpUserMasterRepository tOtpUserMasterRepository) {
    this.systemMasterRepository = systemMasterRepository;
    this.systemUserMasterRepository = systemUserMasterRepository;
    this.tOtpUserMasterRepository = tOtpUserMasterRepository;
  }

  @Bean
  public SystemMasterSPI systemMasterSPI() {
    return new SystemMasterAdapter(systemMasterRepository);
  }

  @Bean
  public SystemUserMasterSPI systemUserMasterSPI() {
    return new SystemUserMasterAdapter(systemUserMasterRepository);
  }

  @Bean
  public TOtpUserMasterSPI tOtpUserMasterSPI() {
    return new TOtpUserMasterAdapter(tOtpUserMasterRepository);
  }
}

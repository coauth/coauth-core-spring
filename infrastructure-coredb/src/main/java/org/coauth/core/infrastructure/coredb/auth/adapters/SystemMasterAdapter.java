package org.coauth.core.infrastructure.coredb.auth.adapters;

import org.coauth.core.commons.constants.ApplicationConstants;
import org.coauth.core.domain.auth.dto.SystemMasterDto;
import org.coauth.core.domain.auth.ports.spi.SystemMasterSPI;
import org.coauth.core.infrastructure.coredb.auth.mappers.SystemMasterMapper;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemMasterRepository;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class SystemMasterAdapter implements SystemMasterSPI {

  private final SystemMasterRepository systemMasterRepository;

  @Value("${infrastructure-coredb.system-id-padding}")
  private int systemUserSpacePadding;

  public SystemMasterAdapter(SystemMasterRepository systemMasterRepository) {
    this.systemMasterRepository = systemMasterRepository;
  }

  @Override
  public Mono<Long> updateDisable(String systemId, LocalDateTime lockedDateTime, String status) {
    return systemMasterRepository.updateDisable(
        StringUtils.rightPad(systemId, systemUserSpacePadding, ApplicationConstants.DB_PAD_CHAR),
        lockedDateTime,
        status);
  }

  @Override
  public Mono<SystemMasterDto> findById(String systemId) {
    return systemMasterRepository
        .findById(
            StringUtils.rightPad(
                systemId, systemUserSpacePadding, ApplicationConstants.DB_PAD_CHAR))
        .flatMap(entity -> Mono.just(SystemMasterMapper.INSTANCE.systemMasterToDto(entity)));
  }
}

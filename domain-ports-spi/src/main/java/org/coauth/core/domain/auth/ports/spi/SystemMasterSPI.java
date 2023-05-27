package org.coauth.core.domain.auth.ports.spi;

import org.coauth.core.domain.auth.dto.SystemMasterDto;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface SystemMasterSPI {

  Mono<Long> updateDisable(String systemId, LocalDateTime lockedDateTime, String status);

  Mono<SystemMasterDto> findById(String systemId);
}

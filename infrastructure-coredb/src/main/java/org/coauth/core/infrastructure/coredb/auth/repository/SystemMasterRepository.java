package org.coauth.core.infrastructure.coredb.auth.repository;

import org.coauth.core.infrastructure.coredb.auth.entity.SystemMasterEntity;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Repository
public interface SystemMasterRepository extends ReactiveCrudRepository<SystemMasterEntity, String> {

  @Modifying
  @Query(
      "UPDATE SYSTEM_MASTER SM SET SM.MODIFICATION_DATE_TIME = :lockedDateTime, SM.STATUS = :status WHERE SM.SYSTEM_ID = :systemId")
  Mono<Long> updateDisable(
      @Param(value = "systemId") String systemId,
      @Param(value = "lockedDateTime") LocalDateTime lockedDateTime,
      @Param(value = "status") String status);
}

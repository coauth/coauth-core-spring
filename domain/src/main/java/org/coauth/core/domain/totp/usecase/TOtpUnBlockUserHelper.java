package org.coauth.core.domain.totp.usecase;

import org.coauth.core.commons.constants.ApplicationConstants;
import org.coauth.core.commons.constants.ApplicationConstants.RecordStatus;
import org.coauth.core.commons.exception.NonFatalException;
import org.coauth.core.commons.utils.DateTimeUtils;
import org.coauth.core.domain.totp.dto.TOtpUnBlockUserDto;
import org.coauth.core.domain.totp.dto.TOtpUserMasterDto;
import org.coauth.core.domain.totp.ports.spi.TOtpUserMasterSPI;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TOtpUnBlockUserHelper {

  TOtpUserMasterSPI tOtpUserMasterSPI;

  TOtpUnBlockUserHelper(TOtpUserMasterSPI tOtpUserMasterSPI) {
    this.tOtpUserMasterSPI = tOtpUserMasterSPI;
  }

  public Boolean isUserDisabledOrActive(TOtpUserMasterDto user) {
    return (user.getStatus().equals(ApplicationConstants.RecordStatus.ACTIVE.getValue()) || user.getStatus().equals(
        RecordStatus.DISABLED.getValue()));
  }

  public Mono<TOtpUnBlockUserDto> changeFlagInDatabase(
      String userSystemId, TOtpUserMasterDto user) {
    return tOtpUserMasterSPI
        .removeDisabledStatus(
            userSystemId,
            DateTimeUtils.getCurrentLocalDateTime(),
            ApplicationConstants.RecordStatus.INACTIVE.getValue())
        .flatMap(
            status ->
                Mono.just(
                    TOtpUnBlockUserDto.builder()
                        .statusCode("200")
                        .statusDescription(
                            user.getStatus()
                                .equals(ApplicationConstants.RecordStatus.ACTIVE.getValue())
                                ? "TOTP subscription cancelled for user"
                                : "User unblocked successfully.")
                        .build()))
        .switchIfEmpty(Mono.error(new NonFatalException("500", "Failure in updating")));
  }

  public Mono<TOtpUnBlockUserDto> formatNoSubscriptionMessage() {
    return Mono.just(
        TOtpUnBlockUserDto.builder()
            .statusCode("404")
            .statusDescription("No active subscription found for user")
            .build());
  }

  public Mono<TOtpUnBlockUserDto> fallbackMethod(Throwable error) {
    if (error instanceof NonFatalException nonFatalException) {
      return Mono.just(
          TOtpUnBlockUserDto.builder()
              .statusCode(nonFatalException.getErrCode())
              .statusDescription(error.getMessage())
              .build());
    } else {
      return Mono.just(
          TOtpUnBlockUserDto.builder()
              .statusCode("500")
              .statusDescription("Unknown error occurred")
              .build());
    }
  }
}

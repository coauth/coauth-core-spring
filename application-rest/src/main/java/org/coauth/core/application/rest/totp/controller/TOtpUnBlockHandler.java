package org.coauth.core.application.rest.totp.controller;

import org.coauth.core.application.rest.totp.json.GenericRequest;
import org.coauth.core.application.rest.totp.mappers.TOtpRequestResponseDtoMapper;
import org.coauth.core.application.rest.validator.AbstractValidationHandler;
import org.coauth.core.commons.auth.config.FetchPrincipalComponent;
import org.coauth.core.domain.auth.dto.UserDto;
import org.coauth.core.domain.totp.dto.TOtpUnBlockUserDto;
import org.coauth.core.domain.totp.ports.api.TOtpUserServiceAPI;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TOtpUnBlockHandler extends AbstractValidationHandler<GenericRequest, Validator> {

  private final TOtpUserServiceAPI tOtpUserServiceAPI;

  private final FetchPrincipalComponent fetchPrincipalComponent;

  TOtpUnBlockHandler(
      Validator validator,
      TOtpUserServiceAPI tOtpUserServiceAPI,
      FetchPrincipalComponent fetchPrincipalComponent) {
    super(GenericRequest.class, validator);
    this.tOtpUserServiceAPI = tOtpUserServiceAPI;
    this.fetchPrincipalComponent = fetchPrincipalComponent;
  }

  @Override
  public Mono<ServerResponse> processBody(
      GenericRequest genericRequest, ServerRequest serverRequest) {

    return fetchPrincipalComponent
        .getAuthDetails()
        .flatMap(user -> callService(user, genericRequest))
        .flatMap(this::processSuccessResponse)
        .switchIfEmpty(processEmpty());
  }

  private Mono<TOtpUnBlockUserDto> callService(UserDto user, GenericRequest genericRequest) {
    return tOtpUserServiceAPI.unBlockUser(
        user.getSystemId().trim(), genericRequest.getUserId().trim());
  }

  private Mono<ServerResponse> processSuccessResponse(TOtpUnBlockUserDto tOtpUnBlockUserDto) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                TOtpRequestResponseDtoMapper.INSTANCE.unblockResponseFromDto(tOtpUnBlockUserDto)));
  }

  private Mono<ServerResponse> processEmpty() {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                TOtpUnBlockUserDto.builder()
                    .statusCode("404")
                    .statusDescription("No active subscription")
                    .build()));
  }
}

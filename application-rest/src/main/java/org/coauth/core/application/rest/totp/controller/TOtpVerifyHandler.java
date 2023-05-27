package org.coauth.core.application.rest.totp.controller;

import org.coauth.core.application.rest.totp.json.GenericResponse;
import org.coauth.core.application.rest.totp.json.VerifyTOtpRequest;
import org.coauth.core.application.rest.totp.mappers.TOtpRequestResponseDtoMapper;
import org.coauth.core.application.rest.validator.AbstractValidationHandler;
import org.coauth.core.commons.auth.config.FetchPrincipalComponent;
import org.coauth.core.domain.auth.dto.UserDto;
import org.coauth.core.domain.totp.dto.TOtpVerifyDto;
import org.coauth.core.domain.totp.ports.api.TOtpUserServiceAPI;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TOtpVerifyHandler extends AbstractValidationHandler<VerifyTOtpRequest, Validator> {

  private final TOtpUserServiceAPI tOtpUserServiceAPI;
  private final FetchPrincipalComponent fetchPrincipalComponent;

  TOtpVerifyHandler(
      Validator validator,
      TOtpUserServiceAPI tOtpUserServiceAPI,
      FetchPrincipalComponent fetchPrincipalComponent) {
    super(VerifyTOtpRequest.class, validator);
    this.tOtpUserServiceAPI = tOtpUserServiceAPI;
    this.fetchPrincipalComponent = fetchPrincipalComponent;
  }

  @Override
  public Mono<ServerResponse> processBody(
      VerifyTOtpRequest verifyTOtpRequest, ServerRequest serverRequest) {
    return fetchPrincipalComponent
        .getAuthDetails()
        .flatMap(user -> callService(user, verifyTOtpRequest))
        .flatMap(this::processSuccessResponse)
        .switchIfEmpty(processEmpty());
  }

  private Mono<TOtpVerifyDto> callService(UserDto user, VerifyTOtpRequest verifyTOtpRequest) {
    return tOtpUserServiceAPI.verify(
        user.getSystemId().trim(),
        verifyTOtpRequest.getUserId().trim(),
        verifyTOtpRequest.getOtp());
  }

  private Mono<ServerResponse> processSuccessResponse(TOtpVerifyDto tOtpVerifyDto) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                TOtpRequestResponseDtoMapper.INSTANCE.verifyResponseFromDto(tOtpVerifyDto)));
  }

  private Mono<ServerResponse> processEmpty() {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                GenericResponse.builder()
                    .statusCode("300")
                    .statusDescription("No active subscription")
                    .build()));
  }
}

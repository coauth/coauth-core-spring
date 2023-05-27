package org.coauth.core.application.rest.auth.controller;

import org.coauth.core.application.rest.auth.json.LoginRequest;
import org.coauth.core.application.rest.auth.json.LoginResponse;
import org.coauth.core.application.rest.totp.json.GenericResponse;
import org.coauth.core.application.rest.validator.AbstractValidationHandler;
import org.coauth.core.domain.auth.dto.UserDto;
import org.coauth.core.domain.auth.ports.api.AuthServiceAPI;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller("systemHandler")
public class SystemHandler extends AbstractValidationHandler<LoginRequest, Validator> {

  private final AuthServiceAPI authServiceAPI;

  SystemHandler(Validator validator, AuthServiceAPI authServiceAPI) {
    super(LoginRequest.class, validator);
    this.authServiceAPI = authServiceAPI;
  }

  @Override
  public Mono<ServerResponse> processBody(LoginRequest loginRequest, ServerRequest serverRequest) {

    return authServiceAPI
        .authenticate(
            loginRequest.getSystemId(), loginRequest.getUserId(), loginRequest.getUserSecret())
        .flatMap(this::prepareSuccessResponse)
        .onErrorResume(this::prepareErrorResponse)
        .switchIfEmpty(prepareOnEmptyResponse());
  }

  private Mono<ServerResponse> prepareSuccessResponse(UserDto userDto) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                LoginResponse.builder()
                    .statusCode("200")
                    .accessToken(userDto.getPassword())
                    .expiry(userDto.getExpiryTime())
                    .build()));
  }

  private Mono<ServerResponse> prepareErrorResponse(Throwable e) {
    return ServerResponse.badRequest()
        .body(BodyInserters.fromValue(GenericResponse.builder()
            .statusCode("400")
            .statusDescription(e.getMessage())
            .build()));
  }

  private Mono<ServerResponse> prepareOnEmptyResponse() {
    return ServerResponse.badRequest()
        .body(BodyInserters.fromValue(GenericResponse.builder()
            .statusCode("400")
            .statusDescription("User does not exist")
            .build()));
  }
}

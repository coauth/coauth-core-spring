package org.coauth.core.application.rest.validator;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.coauth.core.application.rest.auth.json.ApiResponse;
import org.coauth.core.application.rest.totp.json.GenericRequest;
import org.coauth.core.application.rest.totp.json.GenericResponse;
import org.coauth.core.domain.auth.dto.UserDto;
import org.coauth.core.domain.auth.ports.api.AuthServiceAPI;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class DummyHandlerWithoutRequestBody
    extends AbstractValidationHandler<GenericRequest, Validator> {

  private final AuthServiceAPI authServiceAPI;

  DummyHandlerWithoutRequestBody(Validator validator, AuthServiceAPI authServiceAPI) {
    super(null, validator);
    this.authServiceAPI = authServiceAPI;
  }

  public RouterFunction<ServerResponse> doSomething() {
    return route(POST("/test-no-request-body"), this::handleRequest);
  }

  @Override
  public Mono<ServerResponse> processBody(
      GenericRequest genericRequest, ServerRequest serverRequest) {

    return authServiceAPI
        .authenticate("", "", "")
        .flatMap(this::prepareSuccessResponse)
        .onErrorResume(this::prepareErrorResponse)
        .switchIfEmpty(prepareOnEmptyResponse());
  }

  private Mono<ServerResponse> prepareSuccessResponse(UserDto userDto) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            BodyInserters.fromValue(
                GenericResponse.builder().statusCode("200").statusDescription("Success").build()));
  }

  private Mono<ServerResponse> prepareErrorResponse(Throwable e) {
    return ServerResponse.badRequest()
        .body(BodyInserters.fromValue(new ApiResponse(400, e.getMessage(), null)));
  }

  private Mono<ServerResponse> prepareOnEmptyResponse() {
    return ServerResponse.badRequest()
        .body(BodyInserters.fromValue(new ApiResponse(400, "Empty Response", null)));
  }
}

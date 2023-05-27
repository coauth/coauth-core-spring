package org.coauth.core.application.rest.exception;

import org.coauth.core.application.rest.auth.json.LoginRequest;
import org.coauth.core.application.rest.validator.AbstractValidationHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Configuration
public class DummyResponseStatusHandler extends AbstractValidationHandler<LoginRequest, Validator> {


  DummyResponseStatusHandler(Validator validator) {
    super(null, validator);
  }

  @Override
  public Mono<ServerResponse> processBody(LoginRequest loginRequest, ServerRequest serverRequest) {
    return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
  }

}

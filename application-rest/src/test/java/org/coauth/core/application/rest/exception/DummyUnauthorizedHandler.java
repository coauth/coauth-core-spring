package org.coauth.core.application.rest.exception;

import org.coauth.core.application.rest.auth.json.LoginRequest;
import org.coauth.core.application.rest.validator.AbstractValidationHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class DummyUnauthorizedHandler extends AbstractValidationHandler<LoginRequest, Validator> {


  DummyUnauthorizedHandler(Validator validator) {
    super(null, validator);
  }

  @Override
  public Mono<ServerResponse> processBody(LoginRequest loginRequest, ServerRequest serverRequest) {
    return Mono.error(new UnAuthorizedException("Custom unauthorized exception"));
  }

}

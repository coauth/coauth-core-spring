package org.coauth.core.orchestration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(
    basePackages = {"org.coauth.core.infrastructure.coredb"},
    basePackageClasses = {})
@OpenAPIDefinition(
    info = @Info(title = "CoAuth API", version = "1.0", description = "Documentation APIs v1.0"))
@SpringBootApplication(scanBasePackages = "org.coauth.core")
public class CoAuthApplication {

  /**
   * this annotation is added to ignore main class test from coverage report
   */
  @Generated
  public static void main(String[] args) {
    SpringApplication.run(CoAuthApplication.class, args);
  }
}

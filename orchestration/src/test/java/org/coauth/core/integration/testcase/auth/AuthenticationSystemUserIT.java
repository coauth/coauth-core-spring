package org.coauth.core.integration.testcase.auth;

import org.coauth.core.application.rest.auth.json.LoginRequest;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemMasterRepository;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemUserMasterRepository;
import org.coauth.core.integration.support.AuthCreateDbObjectsIT;
import org.coauth.core.integration.support.TestContainerSetupIT;
import org.coauth.core.orchestration.CoAuthApplication;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.main.web-application-type=reactive",
    classes = CoAuthApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@Testcontainers
@ActiveProfiles("test")
@Import({TestContainerSetupIT.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationSystemUserIT {

  private static final String URI = "/auth/login";
  private final String USER = "TEST_USER";
  private final String PASSWORD = "TEST_PASSWORD";
  private final String SYSTEM = "TEST_SYSTEM";
  @Autowired
  SystemUserMasterRepository systemUserMasterRepository;
  @Autowired
  SystemMasterRepository systemMasterRepository;
  @Autowired
  private WebTestClient webTestClient;
  @LocalServerPort
  private int port;

  @PostConstruct
  public void initialise() {
    webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  @Order(1)
  void authNoBody_Test() {
    AuthCreateDbObjectsIT authCreateDbObjectsIT =
        new AuthCreateDbObjectsIT(systemUserMasterRepository, systemMasterRepository);
    authCreateDbObjectsIT.loadData();

    webTestClient
        .post()
        .uri(URI)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(2)
  void authFieldsEmptyFields_Test() {
    LoginRequest loginRequest = LoginRequest.builder().build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(3)
  void authFieldsNoPasswordFields_Test() {
    LoginRequest loginRequest = LoginRequest.builder().systemId(SYSTEM).userId(USER).build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(4)
  void authFieldsNoSystemFields_Test() {
    LoginRequest loginRequest = LoginRequest.builder().userSecret(PASSWORD).userId(USER).build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(5)
  void authFieldsNoUserFields_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userSecret(PASSWORD).systemId(SYSTEM).build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(6)
  void authFieldsNoSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("U1").userSecret("Test@1234").systemId("SYS_X").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(7)
  void authSystemDisabled_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("U1").userSecret("Test@1234").systemId("SYS_D").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(8)
  void authSystemInActive_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("U1").userSecret("Test@1234").systemId("SYS_N").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(9)
  void authNoUserInSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UX").userSecret("Test@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(10)
  void authUserDisabledInSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UD").userSecret("Test@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(11)
  void authUserInActiveInSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UN").userSecret("Test@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(12)
  void authUserActiveInSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UA").userSecret("Test@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("200");
  }

  @Test
  @Order(13)
  void authUserInCorrectPasswordInSystem_InDb_Test() {
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UA").userSecret("Tesat@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(14)
  void authUserDisabledFailAttemptsInSystem_InDb_Test() {
    for (int i = 0; i < 5; i++) {
      LoginRequest loginRequest =
          LoginRequest.builder().userId("UA").userSecret("Tesat@1234").systemId("SYS_A").build();
      webTestClient
          .post()
          .uri(URI)
          .bodyValue(loginRequest)
          .exchange()
          .expectStatus()
          .isBadRequest()
          .expectBody()
          .consumeWith(System.out::println);
    }
    LoginRequest loginRequest =
        LoginRequest.builder().userId("UA").userSecret("Tesat@1234").systemId("SYS_A").build();
    webTestClient
        .post()
        .uri(URI)
        .bodyValue(loginRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400")
        .jsonPath("$.statusDescription")
        .isEqualTo("Your access is inactive or disabled");
  }
}

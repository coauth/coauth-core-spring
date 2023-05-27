package org.coauth.core.integration.testcase.totp;

import org.coauth.core.application.rest.totp.json.GenericRequest;
import org.coauth.core.domain.auth.dto.Role;
import org.coauth.core.domain.auth.dto.UserDto;
import org.coauth.core.domain.auth.ports.spi.JWTUtilSPI;
import org.coauth.core.domain.totp.ports.spi.TOtpCryptoSPI;
import org.coauth.core.domain.totp.ports.spi.TOtpUserMasterSPI;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemMasterRepository;
import org.coauth.core.infrastructure.coredb.auth.repository.SystemUserMasterRepository;
import org.coauth.core.integration.support.TOtpCreateDbObjectsIT;
import org.coauth.core.integration.support.TestContainerSetupIT;
import org.coauth.core.orchestration.CoAuthApplication;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = "spring.main.web-application-type=reactive",
    classes = CoAuthApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "5000000")
@Testcontainers
@ActiveProfiles("test")
@Import({TestContainerSetupIT.class})
@TestMethodOrder(OrderAnnotation.class)
@Isolated("aaa6")
@Order(6)
@TestInstance(Lifecycle.PER_CLASS)
class GetUserStatusIT {

  private static final String URI = "/totp/status";
  private static String validToken;
  private final String USER = "TEST_USER";
  private final String PASSWORD = "TEST_PASSWORD";
  private final String SYSTEM = "TEST_SYSTEM";
  @Autowired
  SystemUserMasterRepository systemUserMasterRepository;
  @Autowired
  SystemMasterRepository systemMasterRepository;
  @Autowired
  TOtpCryptoSPI tOtpCryptoSPI;
  @Autowired
  TOtpUserMasterSPI tOtpUserMasterSPI;

  @Qualifier("jwtUtilspi")
  @Autowired
  JWTUtilSPI jwtUtilSPI;

  @Autowired
  private WebTestClient webTestClient;
  @LocalServerPort
  private int port;

  @BeforeAll
  public void initialise() {
    webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    UserDto userDto =
        UserDto.builder().username("UA").systemId("SYS_A").roles(List.of(Role.ROLE_ADMIN)).build();
    validToken = jwtUtilSPI.generateToken(userDto);

    TOtpCreateDbObjectsIT tOtpCreateDbObjectsIT =
        new TOtpCreateDbObjectsIT(
            systemUserMasterRepository, systemMasterRepository, tOtpUserMasterSPI, tOtpCryptoSPI);
    tOtpCreateDbObjectsIT.loadData();
  }

  @Test
  @Order(1)
  void noToken_Test() {

    webTestClient
        .post()
        .uri(URI)
        .exchange()
        .expectStatus()
        .isUnauthorized()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  @Order(1)
  void badToken_Test() {
    String token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    webTestClient
        .post()
        .uri(URI)
        .headers(headers -> headers.setBearerAuth(token))
        .exchange()
        .expectStatus()
        .isUnauthorized()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  @Order(1)
  void expiredToken_Test() {

    String token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzeXN0ZW1JZCI6Ik5FVEJLIiwicm9sZSI6WyJST0xFX0FETUlOIl0sInN1YiI6IjAzODJiOWRlLWZlY2EtNDk0Ny1iYTcyLWM3NWNkNTJlNTFmMCIsImlhdCI6MTY4MTQ5MjMzNSwiZXhwIjoxNjgxNDk1OTM1fQ.-YJF3ZPaFjk8FIfq-O_q6bSYPDxWJKc0_P1s12-9brjqrJ_LMD5ncNSRWnJZvg94MnK42EscjHMHtIsemVai9g";
    webTestClient
        .post()
        .uri(URI)
        .headers(headers -> headers.setBearerAuth(token))
        .exchange()
        .expectStatus()
        .isUnauthorized()
        .expectBody()
        .consumeWith(System.out::println);
  }

  @Test
  @Order(1)
  void requestFieldsNotSet_Test() {
    webTestClient
        .post()
        .uri(URI)
        .headers(headers -> headers.setBearerAuth(validToken))
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @Test
  @Order(1)
  void requestFieldsNull_Test() {
    GenericRequest genericRequest = new GenericRequest();

    webTestClient
        .post()
        .uri(URI)
        .headers(headers -> headers.setBearerAuth(validToken))
        .bodyValue(genericRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo("400");
  }

  @ParameterizedTest
  @CsvSource({"_STATUS_UA5,404", "_STATUS_UD,405", "_STATUS_UN,404", "_STATUS_UA1,200"})
  @Order(4)
  void userNotInDBAndDisabledAndInActiveAndActive_Test(String userId, String statusCode) {
    GenericRequest genericRequest = new GenericRequest();
    genericRequest.setUserId(userId);

    webTestClient
        .post()
        .uri(URI)
        .headers(headers -> headers.setBearerAuth(validToken))
        .bodyValue(genericRequest)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.statusCode")
        .isEqualTo(statusCode);
  }
}

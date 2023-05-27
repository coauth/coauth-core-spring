package org.coauth.core.domain.totp.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.coauth.core.commons.exception.NonFatalException;
import org.coauth.core.domain.totp.dto.TOtpUserMasterDto;
import org.coauth.core.domain.totp.ports.spi.TOtpCryptoSPI;
import org.coauth.core.domain.totp.ports.spi.TOtpUserMasterSPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = {TOtpGenerateQrHelper.class})
@ExtendWith(SpringExtension.class)
class TOtpGenerateQrHelperTest {

  @MockBean
  private TOtpCryptoSPI tOtpCryptoSPI;

  @Autowired
  private TOtpGenerateQrHelper tOtpGenerateQrHelper;

  @MockBean
  private TOtpUserMasterSPI tOtpUserMasterSPI;

  /**
   * Method under test: {@link TOtpGenerateQrHelper#canGenerateQr(TOtpUserMasterDto)}
   */
  @ParameterizedTest
  @ValueSource(strings = {"Status", "A", "N"})
  void testCanGenerateQr(String s) {
    TOtpUserMasterDto user = mock(TOtpUserMasterDto.class);
    when(user.getStatus()).thenReturn(s);
    tOtpGenerateQrHelper.canGenerateQr(user);
    verify(user, atLeast(1)).getStatus();
  }

  /**
   * Method under test: {@link TOtpGenerateQrHelper#canGenerateQr(TOtpUserMasterDto)}
   */
  @Test
  void testCanGenerateQr2() {
    TOtpUserMasterDto user = mock(TOtpUserMasterDto.class);
    when(user.getStatus()).thenReturn("D");
    tOtpGenerateQrHelper.canGenerateQr(user);
    verify(user).getStatus();
  }

  /**
   * Method under test: {@link TOtpGenerateQrHelper#generateQr(String, TOtpUserMasterDto)}
   */
  @Test
  void testGenerateQr() {
    when(tOtpCryptoSPI.generateQRCode(
        Mockito.any(),
        Mockito.any(),
        Mockito.any(),
        Mockito.any()))
        .thenReturn("Generate QRCode");
    TOtpUserMasterDto user = mock(TOtpUserMasterDto.class);
    when(user.getUserSecret()).thenReturn("User Secret");
    tOtpGenerateQrHelper.generateQr("42", user);
    verify(tOtpCryptoSPI)
        .generateQRCode(
            Mockito.any(),
            Mockito.any(),
            Mockito.any(),
            Mockito.any());
    verify(user).getUserSecret();
  }

  /**
   * Method under test: {@link TOtpGenerateQrHelper#formatNoSubscriptionMessage()}
   */
  @Test
  void testFormatNoSubscriptionMessage() {

    assertDoesNotThrow(() -> tOtpGenerateQrHelper.formatNoSubscriptionMessage());
  }

  @Test
  void fallbackMethod_Test() {

    NonFatalException nfe = new NonFatalException("300", "Sample Exception");

    Exception e = new Exception("Sample Other Exception");

    StepVerifier.create(tOtpGenerateQrHelper.fallbackMethod(nfe))
        .assertNext(
            tOtpCreateNewDto -> {
              assertEquals(tOtpCreateNewDto.getStatusDescription(), nfe.getMessage());
              assertEquals(tOtpCreateNewDto.getStatusCode(), nfe.getErrCode());
            })
        .verifyComplete();

    StepVerifier.create(tOtpGenerateQrHelper.fallbackMethod(e))
        .assertNext(
            tOtpCreateNewDto -> {
              assertEquals("Unknown error occurred", tOtpCreateNewDto.getStatusDescription());
            })
        .verifyComplete();
  }
}

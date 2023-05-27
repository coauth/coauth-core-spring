package org.coauth.core.domain.totp.ports.spi;

public interface TOtpCryptoSPI {

  String generateSecretKey(String userId);

  Boolean verify(String plainTextOtp, String userId, String encryptedSecret);

  String generateQRCode(String userId, String encryptedSecret, String email, String appName);

  String getPlainTextSecret(String userId, String encryptedSecret);
}

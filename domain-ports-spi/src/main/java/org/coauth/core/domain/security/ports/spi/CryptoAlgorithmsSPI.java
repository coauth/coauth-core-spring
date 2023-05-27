package org.coauth.core.domain.security.ports.spi;

public interface CryptoAlgorithmsSPI {

  public String generateHashFromSecret(String systemId, String userId, String secret);
}

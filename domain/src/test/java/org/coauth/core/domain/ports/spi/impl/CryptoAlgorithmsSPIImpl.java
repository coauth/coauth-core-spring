package org.coauth.core.domain.ports.spi.impl;

import org.coauth.core.domain.security.ports.spi.CryptoAlgorithmsSPI;
import org.springframework.stereotype.Component;

@Component
public class CryptoAlgorithmsSPIImpl implements CryptoAlgorithmsSPI {

  @Override
  public String generateHashFromSecret(String systemId, String userId, String secret) {
    return null;
  }
}

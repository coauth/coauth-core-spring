package com.github.godwinpinto.authable.infrastructure.crypto.adapters;

import com.github.godwinpinto.authable.domain.auth.dto.UserDto;
import com.github.godwinpinto.authable.domain.auth.ports.spi.JWTUtilSPI;
import com.github.godwinpinto.authable.infrastructure.crypto.service.JWTUtilService;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JWTUtilAdapter implements JWTUtilSPI {

  private JWTUtilService jwtUtilService;

  public JWTUtilAdapter(JWTUtilService jwtUtilService) {
    this.jwtUtilService = jwtUtilService;
  }

  @Override
  public String getUsernameFromToken(String token) {
    return jwtUtilService.getUsernameFromToken(token);
  }

  @Override
  public Boolean validateToken(String token) {
    return jwtUtilService.validateToken(token);
  }

  @Override
  public Map<String, Object> getClaims(String token) {
    return jwtUtilService.getAllClaimsFromToken(token);
  }

  @Override
  public String generateToken(UserDto userDto) {
    return jwtUtilService.generateToken(userDto);
  }

  @Override
  public Date getExpirationDateFromToken(String token) {
    return jwtUtilService.getExpirationDateFromToken(token);
  }
}

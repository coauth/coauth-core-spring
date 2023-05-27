package org.coauth.core.application.rest.totp.json;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTOtpRequest {

  @NotBlank(message = "User Id cannot be empty")
  private String userId;

  @NotBlank(message = "OTP cannot be empty")
  private String otp;
}

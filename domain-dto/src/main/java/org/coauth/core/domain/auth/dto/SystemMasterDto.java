package org.coauth.core.domain.auth.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SystemMasterDto {

  private String systemId;

  private String systemName;

  private String systemDescription;

  private String status;

  private String modificationId;

  private LocalDateTime modificationDateTime;

  private String creationId;

  private LocalDateTime creationDateTime;
}

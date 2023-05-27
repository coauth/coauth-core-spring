package org.coauth.core.infrastructure.coredb.totp.mappers;

import org.coauth.core.domain.totp.dto.TOtpUserMasterDto;
import org.coauth.core.infrastructure.coredb.totp.entity.TOtpUserMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TOtpUserMasterMapper {

  TOtpUserMasterMapper INSTANCE = Mappers.getMapper(TOtpUserMasterMapper.class);

  TOtpUserMasterDto tOtpUserMasterToDto(TOtpUserMasterEntity tOtpUserMasterEntity);

  TOtpUserMasterEntity dtoToEntity(TOtpUserMasterDto tOtpUserMasterDto);
}

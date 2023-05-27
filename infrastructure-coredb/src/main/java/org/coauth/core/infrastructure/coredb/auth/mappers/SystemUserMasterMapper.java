package org.coauth.core.infrastructure.coredb.auth.mappers;

import org.coauth.core.domain.auth.dto.SystemUserMasterDto;
import org.coauth.core.infrastructure.coredb.auth.entity.SystemUserMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SystemUserMasterMapper {

  SystemUserMasterMapper INSTANCE = Mappers.getMapper(SystemUserMasterMapper.class);

  SystemUserMasterDto systemUserMasterToDto(SystemUserMasterEntity systemUserMasterEntity);
}

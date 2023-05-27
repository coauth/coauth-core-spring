package org.coauth.core.infrastructure.coredb.auth.mappers;

import org.coauth.core.domain.auth.dto.SystemMasterDto;
import org.coauth.core.infrastructure.coredb.auth.entity.SystemMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SystemMasterMapper {

  SystemMasterMapper INSTANCE = Mappers.getMapper(SystemMasterMapper.class);

  SystemMasterDto systemMasterToDto(SystemMasterEntity systemMasterEntity);
}

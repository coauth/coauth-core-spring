package org.coauth.core.application.rest.totp.mappers;

import org.coauth.core.application.rest.totp.json.CreateNewTOtpResponse;
import org.coauth.core.application.rest.totp.json.GenerateQrResponse;
import org.coauth.core.application.rest.totp.json.GenericResponse;
import org.coauth.core.domain.totp.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TOtpRequestResponseDtoMapper {

  TOtpRequestResponseDtoMapper INSTANCE = Mappers.getMapper(TOtpRequestResponseDtoMapper.class);

  CreateNewTOtpResponse createNewResponseFromDto(TOtpCreateNewDto tOtpCreateNewDto);

  GenericResponse unblockResponseFromDto(TOtpUnBlockUserDto tOtpUnBlockUserDto);

  GenericResponse unSubscribeResponseFromDto(TOtpUnSubscribeUserDto tOtpUnSubscribeUserDto);

  GenericResponse statusResponseFromDto(TOtpUserStatusDto tOtpUserStatusDto);

  GenericResponse verifyResponseFromDto(TOtpVerifyDto tOtpVerifyDto);

  GenerateQrResponse generateQrResponseFromDto(TOtpGenerateQrDto tOtpGenerateQrDto);
}

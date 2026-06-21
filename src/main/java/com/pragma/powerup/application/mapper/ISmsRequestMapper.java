package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.SmsRequestDto;
import com.pragma.powerup.domain.model.SmsModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ISmsRequestMapper {

    SmsModel toModel(SmsRequestDto smsRequestDto);
}

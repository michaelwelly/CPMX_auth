package com.croco.auth.mapper;

import com.croco.auth.dto.EventDTO;
import com.croco.auth.dto.SecurityEventDTO;
import com.croco.auth.entity.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    // Пример, если userId соответствует loginName
    @Mapping(target = "systemPart", constant = "DefaultSystemPart")
    @Mapping(target = "fieldName", source = "loginName")
    @Mapping(target = "fieldValueBefore", constant = "")
    @Mapping(target = "fieldValueAfter", constant = "eventType")
    @Mapping(target = "changeReason", source = "details")
    @Mapping(target = "description", source = "eventType")
    EventDTO toEventDTO(SecurityEventDTO securityEventDTO);
}

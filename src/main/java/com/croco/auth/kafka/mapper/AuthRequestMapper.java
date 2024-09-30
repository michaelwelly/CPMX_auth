package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.xml.AuthRequestXmlDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.io.ByteArrayInputStream;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthRequestMapper {
    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    AuthRequestXmlDTO toXml(AuthRequestDTO authRequestDTO);

    AuthRequestDTO toDto(AuthRequestXmlDTO authRequestXmlDTO);

    default AuthRequestXmlDTO toXml(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuthRequestXmlDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (AuthRequestXmlDTO) unmarshaller.unmarshal(new ByteArrayInputStream(data));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }
}
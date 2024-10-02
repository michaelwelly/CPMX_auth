package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.SecurityEventDTO;
import com.croco.auth.dto.xml.SecurityEventXmlDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SecurityEventMapper {
    SecurityEventMapper INSTANCE = Mappers.getMapper(SecurityEventMapper.class);

    SecurityEventDTO toDto(SecurityEventXmlDTO securityEventXmlDTO);

    SecurityEventXmlDTO toXml(SecurityEventDTO securityEventDTO);

    default SecurityEventXmlDTO toXml(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SecurityEventXmlDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (SecurityEventXmlDTO) unmarshaller.unmarshal(new ByteArrayInputStream(data));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }

    default byte[] toByte(SecurityEventXmlDTO data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SecurityEventXmlDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(data, outputStream);
            return outputStream.toByteArray();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to serialize object to XML", e);
        }
    }
}
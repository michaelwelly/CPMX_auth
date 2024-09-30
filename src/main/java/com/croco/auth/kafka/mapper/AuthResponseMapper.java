package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.xml.AuthResponseXmlDTO;
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
public interface AuthResponseMapper {
    AuthResponseMapper INSTANCE = Mappers.getMapper(AuthResponseMapper.class);

    AuthResponseDTO toDto(AuthResponseXmlDTO authResponseXmlDTO);

    AuthResponseXmlDTO toXml(AuthResponseDTO authResponseDTO);

    default AuthResponseXmlDTO toXml(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuthResponseXmlDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (AuthResponseXmlDTO) unmarshaller.unmarshal(new ByteArrayInputStream(data));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }

    default byte[] toByte(AuthResponseXmlDTO data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuthResponseXmlDTO.class);
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
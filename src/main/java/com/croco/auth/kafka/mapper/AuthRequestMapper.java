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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthRequestMapper {
    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    default AuthRequestXmlDTO toXml(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AuthRequestXmlDTO.class);

            String logdata = new String (data);
            return createAuthRequestXmlDTO(logdata);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to deserialize XML", e);
        }
    }

    default AuthRequestXmlDTO createAuthRequestXmlDTO(String xmlData) {
        try {
            // Создаем DocumentBuilderFactory и DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Парсим XML-строку в Document
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));

            // Получаем корневой элемент
            Element root = document.getDocumentElement();

            // Извлекаем данные из XML
            String hashedPassword = getElementValue(root, "HashedPassword");
            String loginName = getElementValue(root, "LoginName");
            String subsystemName = getElementValue(root, "SubsystemName");
            String uuidString = getElementValue(root, "uuid");

            // Создаем объект AuthRequestXmlDTO
            AuthRequestXmlDTO authRequest = new AuthRequestXmlDTO();
            authRequest.setUuid(UUID.fromString(uuidString)); // Преобразуем строку в UUID
            authRequest.setSubsystemName(subsystemName);
            authRequest.setLoginName(loginName);
            authRequest.setHashedPassword(hashedPassword);

            return authRequest;

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Или выбросьте исключение, если это предпочтительнее
        }
    }

    default String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    default AuthRequestDTO toDto(AuthRequestXmlDTO authRequestXmlDTO) {
        if ( authRequestXmlDTO == null ) {
            return null;
        }

        AuthRequestDTO authRequestDTO = new AuthRequestDTO();

        authRequestDTO.setLoginName( authRequestXmlDTO.getLoginName() );
        authRequestDTO.setSubsystemName( authRequestXmlDTO.getSubsystemName() );
        authRequestDTO.setHashedPassword( authRequestXmlDTO.getHashedPassword() );
        authRequestDTO.setUuid(String.valueOf(authRequestXmlDTO.getUUID()));

        return authRequestDTO;
    }
}
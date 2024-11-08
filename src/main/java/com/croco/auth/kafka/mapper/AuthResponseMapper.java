package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.UserAuthorizationStatus;
import com.croco.auth.dto.xml.AuthResponseXmlDTO;
import com.croco.auth.entity.UserStatus;
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
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthResponseMapper {
    AuthResponseMapper INSTANCE = Mappers.getMapper(AuthResponseMapper.class);

    default AuthResponseDTO toDto(AuthResponseXmlDTO authResponseXmlDTO) {
        if ( authResponseXmlDTO == null ) {
            return null;
        }

        AuthResponseDTO.AuthResponseDTOBuilder authResponseDTO = AuthResponseDTO.builder();

        authResponseDTO.userId( authResponseXmlDTO.getUserId() );
        authResponseDTO.loginName( authResponseXmlDTO.getLoginName() );
        authResponseDTO.userDescription( authResponseXmlDTO.getUserDescription() );
        authResponseDTO.userStatus( userAuthorizationStatusToUserStatus( authResponseXmlDTO.getUserStatus() ) );
        authResponseDTO.sessionToken( authResponseXmlDTO.getSessionToken() );
        authResponseDTO.uuid(authResponseXmlDTO.getUUID());
        return authResponseDTO.build();
    }


    default AuthResponseXmlDTO toXml(AuthResponseDTO authResponseDTO) {
        if ( authResponseDTO == null ) {
            return null;
        }

        AuthResponseXmlDTO authResponseXmlDTO = new AuthResponseXmlDTO();

        authResponseXmlDTO.setUuid(String.valueOf(authResponseDTO.getUuid()));
        authResponseXmlDTO.setUserId( authResponseDTO.getUserId() );
        authResponseXmlDTO.setLoginName( authResponseDTO.getLoginName() );
        authResponseXmlDTO.setUserDescription( authResponseDTO.getUserDescription() );
        authResponseXmlDTO.setUserStatus( userStatusToUserAuthorizationStatus( authResponseDTO.getUserStatus() ) );
        authResponseXmlDTO.setSessionToken( authResponseDTO.getSessionToken() );
        authResponseXmlDTO.setUuid(String.valueOf(authResponseDTO.getUuid()));
        return authResponseXmlDTO;
    }

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

    default UserStatus userAuthorizationStatusToUserStatus(UserAuthorizationStatus userAuthorizationStatus) {
        if ( userAuthorizationStatus == null ) {
            return null;
        }

        UserStatus userStatus;

        switch ( userAuthorizationStatus ) {
            case ENABLED: userStatus = UserStatus.ENABLED;
                break;
            case DISABLED: userStatus = UserStatus.DISABLED;
                break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + userAuthorizationStatus );
        }

        return userStatus;
    }

    default  UserAuthorizationStatus userStatusToUserAuthorizationStatus(UserStatus userStatus) {
        if ( userStatus == null ) {
            return null;
        }

        UserAuthorizationStatus userAuthorizationStatus;

        switch ( userStatus ) {
            case ENABLED: userAuthorizationStatus = UserAuthorizationStatus.ENABLED;
                break;
            case DISABLED: userAuthorizationStatus = UserAuthorizationStatus.DISABLED;
                break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + userStatus );
        }

        return userAuthorizationStatus;
    }
}
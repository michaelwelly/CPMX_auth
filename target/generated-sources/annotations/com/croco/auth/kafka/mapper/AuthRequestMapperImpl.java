package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.dto.xml.AuthRequestXmlDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T11:45:45+0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AuthRequestMapperImpl implements AuthRequestMapper {

    @Override
    public AuthRequestXmlDTO toXml(AuthRequestDTO authRequestDTO) {
        if ( authRequestDTO == null ) {
            return null;
        }

        AuthRequestXmlDTO authRequestXmlDTO = new AuthRequestXmlDTO();

        authRequestXmlDTO.setSubsystemName( authRequestDTO.getSubsystemName() );
        authRequestXmlDTO.setLoginName( authRequestDTO.getLoginName() );
        authRequestXmlDTO.setHashedPassword( authRequestDTO.getHashedPassword() );

        return authRequestXmlDTO;
    }

    @Override
    public AuthRequestDTO toDto(AuthRequestXmlDTO authRequestXmlDTO) {
        if ( authRequestXmlDTO == null ) {
            return null;
        }

        AuthRequestDTO authRequestDTO = new AuthRequestDTO();

        authRequestDTO.setLoginName( authRequestXmlDTO.getLoginName() );
        authRequestDTO.setSubsystemName( authRequestXmlDTO.getSubsystemName() );
        authRequestDTO.setHashedPassword( authRequestXmlDTO.getHashedPassword() );

        return authRequestDTO;
    }
}

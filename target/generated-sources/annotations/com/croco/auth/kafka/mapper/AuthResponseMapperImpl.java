package com.croco.auth.kafka.mapper;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.dto.UserAuthorizationStatus;
import com.croco.auth.dto.xml.AuthResponseXmlDTO;
import com.croco.auth.entity.UserStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-27T11:45:44+0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AuthResponseMapperImpl implements AuthResponseMapper {

    @Override
    public AuthResponseDTO toDto(AuthResponseXmlDTO authResponseXmlDTO) {
        if ( authResponseXmlDTO == null ) {
            return null;
        }

        AuthResponseDTO.AuthResponseDTOBuilder authResponseDTO = AuthResponseDTO.builder();

        authResponseDTO.userId( authResponseXmlDTO.getUserId() );
        authResponseDTO.loginName( authResponseXmlDTO.getLoginName() );
        authResponseDTO.userDescription( authResponseXmlDTO.getUserDescription() );
        authResponseDTO.userStatus( userAuthorizationStatusToUserStatus( authResponseXmlDTO.getUserStatus() ) );
        authResponseDTO.sessionToken( authResponseXmlDTO.getSessionToken() );

        return authResponseDTO.build();
    }

    @Override
    public AuthResponseXmlDTO toXml(AuthResponseDTO authResponseDTO) {
        if ( authResponseDTO == null ) {
            return null;
        }

        AuthResponseXmlDTO authResponseXmlDTO = new AuthResponseXmlDTO();

        authResponseXmlDTO.setUserId( authResponseDTO.getUserId() );
        authResponseXmlDTO.setLoginName( authResponseDTO.getLoginName() );
        authResponseXmlDTO.setUserDescription( authResponseDTO.getUserDescription() );
        authResponseXmlDTO.setUserStatus( userStatusToUserAuthorizationStatus( authResponseDTO.getUserStatus() ) );
        authResponseXmlDTO.setSessionToken( authResponseDTO.getSessionToken() );

        return authResponseXmlDTO;
    }

    protected UserStatus userAuthorizationStatusToUserStatus(UserAuthorizationStatus userAuthorizationStatus) {
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

    protected UserAuthorizationStatus userStatusToUserAuthorizationStatus(UserStatus userStatus) {
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

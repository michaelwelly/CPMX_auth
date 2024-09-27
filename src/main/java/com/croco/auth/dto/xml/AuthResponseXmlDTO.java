package com.croco.auth.dto.xml;

import com.croco.auth.dto.UserAuthorizationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "AuthResponse")
@Setter
public class AuthResponseXmlDTO {
     private Long userId;
     private String loginName;
     private String userDescription;
     private UserAuthorizationStatus userStatus;
     private String sessionToken;

     @XmlElement(name = "UserId")
     public Long getUserId() {
          return userId;
     }

     @XmlElement(name = "LoginName")
     public String getLoginName() {
          return loginName;
     }

     @XmlElement(name = "UserDescription")
     public String getUserDescription() {
          return userDescription;
     }

     @XmlElement(name = "UserStatus")
     public UserAuthorizationStatus getUserStatus() {
          return userStatus;
     }

     @XmlElement(name = "SessionToken")
     public String getSessionToken() {
          return sessionToken;
     }
}
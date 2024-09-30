package com.croco.auth.dto.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;


@XmlRootElement(name = "AuthRequest")
@Setter
public class AuthRequestXmlDTO {
     String subsystemName;
     String loginName;
     String hashedPassword;

     @XmlElement(name = "SubsystemName")
     public String getSubsystemName() {
          return subsystemName;
     }

     @XmlElement(name = "LoginName")
     public String getLoginName() {
          return loginName;
     }

     @XmlElement(name = "HashedPassword")
     public String getHashedPassword() {
          return hashedPassword;
     }
}

package com.croco.auth.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "SecurityEvent")
@Setter
public class SecurityEventXmlDTO {
    private String eventType; // Тип события (например, "LOGIN_ATTEMPT", "ACCESS_CHANGE")
    private String loginName; // Имя пользователя
    private String timestamp; // Время события
    private String details; // Дополнительные детали события

    @XmlElement(name = "eventType")
    public String getEventType() {
        return eventType;
    }
    @XmlElement(name = "loginName")
    public String getLoginName() {
        return loginName;
    }
    @XmlElement(name = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }
    @XmlElement(name = "details")
    public String getDetails() {
        return details;
    }
}
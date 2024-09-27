package com.croco.auth.dto.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;

@XmlRootElement(name = "AuthLoginRequest", namespace = "msg")
@XmlType(propOrder = {"messageHeader", "messageBody"})
@Setter
public class AuthLoginRequestXmlDto {

    private MessageHeader messageHeader;
    private MessageBody messageBody;

    @XmlElement(name = "MessageHeader", namespace = "msg")
    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    @XmlElement(name = "MessageBody", namespace = "msg")
    public MessageBody getMessageBody() {
        return messageBody;
    }

    @XmlType(propOrder = {"messageId", "sentTime", "sender", "receiver", "objectType", "priority", "userSecurityTicket", "refId"})
    @Setter
    public static class MessageHeader {
        private String messageId;
        private String sentTime;
        private String sender;
        private String receiver;
        private String objectType;
        private int priority;
        private String userSecurityTicket;
        private String refId;

        @XmlElement(name = "MessageId", namespace = "hd")
        public String getMessageId() {
            return messageId;
        }

        @XmlElement(name = "SentTime", namespace = "hd")
        public String getSentTime() {
            return sentTime;
        }

        @XmlElement(name = "Sender", namespace = "hd")
        public String getSender() {
            return sender;
        }

        @XmlElement(name = "Receiver", namespace = "hd")
        public String getReceiver() {
            return receiver;
        }

        @XmlElement(name = "ObjectType", namespace = "hd")
        public String getObjectType() {
            return objectType;
        }

        @XmlElement(name = "Priority", namespace = "hd")
        public int getPriority() {
            return priority;
        }

        @XmlElement(name = "UserSecurityTicket", namespace = "hd")
        public String getUserSecurityTicket() {
            return userSecurityTicket;
        }

        @XmlElement(name = "RefId", namespace = "hd")
        public String getRefId() {
            return refId;
        }
    }

    @XmlType(propOrder = {"login", "password"})
    public static class MessageBody {
        private String login;
        private String password;

        @XmlElement(name = "Login", namespace = "mb")
        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        @XmlElement(name = "Password", namespace = "mb")
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
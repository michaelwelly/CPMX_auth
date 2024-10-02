package com.croco.auth.kafka;


import com.croco.auth.dto.SecurityEventDTO;

import com.croco.auth.dto.xml.SecurityEventXmlDTO;
import com.croco.auth.kafka.mapper.SecurityEventMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@RequiredArgsConstructor
public class KafkaSecurityLoggerSerializer implements Serializer<SecurityEventDTO> {

    private final SecurityEventMapper securityEventMapper;

    public KafkaSecurityLoggerSerializer() {
        // Инициализация маппера
        this.securityEventMapper = SecurityEventMapper.INSTANCE;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если необходима
    }

    @Override
    public byte[] serialize(String topic, SecurityEventDTO data) {
        if (data == null) {
            return null;
        }
        SecurityEventXmlDTO securityEventXmlDTO =  securityEventMapper.toXml(data);
        return securityEventMapper.toByte(securityEventMapper.toXml(data));
    }

    @Override
    public void close() {
        // Закрытие ресурсов, если необходимо
    }
}
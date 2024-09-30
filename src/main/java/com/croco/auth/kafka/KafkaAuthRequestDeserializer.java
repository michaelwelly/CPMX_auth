package com.croco.auth.kafka;

import com.croco.auth.dto.AuthRequestDTO;
import com.croco.auth.kafka.mapper.AuthRequestMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@RequiredArgsConstructor
public class KafkaAuthRequestDeserializer implements Deserializer<AuthRequestDTO> {

    private final AuthRequestMapper authRequestMapper;

    public KafkaAuthRequestDeserializer() {
        // Инициализация маппера
        this.authRequestMapper = AuthRequestMapper.INSTANCE;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public AuthRequestDTO deserialize(String topic, byte[] data) {
            return authRequestMapper.toDto(authRequestMapper.toXml(data));
    }

    @Override
    public void close() {
    }
}
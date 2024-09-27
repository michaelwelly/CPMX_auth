package com.croco.auth.kafka;

import com.croco.auth.dto.AuthResponseDTO;
import com.croco.auth.kafka.mapper.AuthResponseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@RequiredArgsConstructor
public class KafkaAuthResponseSerializer implements Serializer<AuthResponseDTO> {

    private final AuthResponseMapper authResponseMapper;

    public KafkaAuthResponseSerializer() {
        // Инициализация маппера
        this.authResponseMapper = AuthResponseMapper.INSTANCE;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Конфигурация, если необходима
    }

    @Override
    public byte[] serialize(String topic, AuthResponseDTO data) {
        if (data == null) {
            return null;
        }
        return authResponseMapper.toByte(authResponseMapper.toXml(data));
    }

    @Override
    public void close() {
        // Закрытие ресурсов, если необходимо
    }
}
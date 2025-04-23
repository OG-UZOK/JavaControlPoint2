package com.example.JavaControlPoint2.config;

import com.example.JavaControlPoint2.dto.input.KafkaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("Отправлено сообщение: {} в топик: {}", message, topic);
        return null;
    }

    public Void sendJsonMessage(String topic, KafkaMessage message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(topic, jsonMessage);
        log.info("Отправлено JSON-сообщение: {} в топик: {}", jsonMessage, topic);
        return null;
    }
}

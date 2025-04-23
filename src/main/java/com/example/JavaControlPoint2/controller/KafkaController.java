package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.config.KafkaProducer;
import com.example.JavaControlPoint2.dto.input.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.send.message", havingValue = "true")
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    @SneakyThrows
    @PostMapping("/test/{topic-id}")
    public ResponseEntity<Void> sendKafka(@RequestBody KafkaMessage message, @PathVariable("topic-id") String topicId) {
        return ResponseEntity.ok(kafkaProducer.sendJsonMessage(topicId, message));
    }
}

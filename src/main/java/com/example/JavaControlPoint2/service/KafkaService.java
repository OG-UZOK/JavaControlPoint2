package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.config.KafkaProducer;
import com.example.JavaControlPoint2.dto.input.KafkaMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {

    private final KafkaProducer kafkaProducer;


}

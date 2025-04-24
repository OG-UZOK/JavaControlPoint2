package com.example.JavaControlPoint2.config;

import com.example.JavaControlPoint2.dto.enumeration.RoleEnum;
import com.example.JavaControlPoint2.dto.input.EmployeeMessage;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EmployeeRepository employeeRepository;

    @KafkaListener(topics = "${topic.employee}")
    public void consume(String message) {
        log.info("Received employee message: {}", message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeeMessage employeeMessage = objectMapper.readValue(message, EmployeeMessage.class);

            RoleEnum role = RoleEnum.valueOf(employeeMessage.role());
            Employee employee = new Employee();
            employee.setName(employeeMessage.name());
            employee.setRole(role);

            employeeRepository.save(employee);
            log.info("Saved new employee: {}", employee);

        } catch (IllegalArgumentException e) {
            log.error("Invalid role received");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    }

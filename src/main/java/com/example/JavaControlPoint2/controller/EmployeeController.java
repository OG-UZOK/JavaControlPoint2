package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.dto.input.KafkaMessage;
import com.example.JavaControlPoint2.dto.output.EmployeeOutput;
import com.example.JavaControlPoint2.dto.output.EmployeesId;
import com.example.JavaControlPoint2.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeesId> getEmployeesId() {
        return ResponseEntity.ok(employeeService.getEmployeesId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeOutput> getEmployeesId(@PathVariable("id")UUID id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
}

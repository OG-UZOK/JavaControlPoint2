package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.model.dto.input.EmployeeCreateDto;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeCreateDto employeeCreateDto) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString().replace("-", ""));
        employee.setEmployeeName(employeeCreateDto.employeeName());
        employee.setStatus(employeeCreateDto.status());
        employee.setPosition(employeeCreateDto.position());
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));
    }
}
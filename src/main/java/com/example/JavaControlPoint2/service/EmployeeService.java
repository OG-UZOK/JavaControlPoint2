package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.dto.output.EmployeeOutput;
import com.example.JavaControlPoint2.dto.output.EmployeesId;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.exception.NotFoundException;
import com.example.JavaControlPoint2.repository.EmployeeRepository;
import com.example.JavaControlPoint2.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;

    public EmployeesId getEmployeesId() {
        List<UUID> employeeId = employeeRepository.findAllEmployeesId();
        return new EmployeesId(employeeId);
    }

    @SneakyThrows
    public EmployeeOutput getEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incorrect id"));

        Double amountHours =  reportRepository.findTotalHoursByEmployeeId(employee.getId());

        return new EmployeeOutput(employee.getId(), employee.getName(), employee.getRole(), amountHours);
    }
}

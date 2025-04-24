package com.example.JavaControlPoint2.dto.output;

public record EmployeeWorkHours(
        String employeeId,
        double hoursPerDay,
        double totalHours,
        double salary
) {
}
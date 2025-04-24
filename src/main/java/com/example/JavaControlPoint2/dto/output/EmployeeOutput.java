package com.example.JavaControlPoint2.dto.output;

import com.example.JavaControlPoint2.dto.enumeration.RoleEnum;

import java.util.UUID;

public record EmployeeOutput(
        UUID id,
        String name,
        RoleEnum role,
        Double hours
) {
}

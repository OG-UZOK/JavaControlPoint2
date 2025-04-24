package com.example.JavaControlPoint2.dto.output;

import java.util.List;
import java.util.UUID;

public record EmployeesId(
        List<UUID> employeeId
) {
}

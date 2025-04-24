package com.example.JavaControlPoint2.dto.output;

import java.util.UUID;

public record ReportEntryCurrencyOutput(
        UUID employeeId,
        Double hours,
        String currency,
        Double salary
) {
}

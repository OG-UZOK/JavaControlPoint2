package com.example.JavaControlPoint2.dto.output;

import java.time.LocalDate;
import java.util.UUID;

public record ReportsOutput(
        UUID reportId,
        LocalDate startDate,
        LocalDate endDate
) {
}

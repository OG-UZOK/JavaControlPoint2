package com.example.JavaControlPoint2.dto.output;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReportCurrencyOutput(
        UUID id,
        LocalDate startDate,
        LocalDate endDate,
        List<ReportEntryCurrencyOutput> entries
) {
}

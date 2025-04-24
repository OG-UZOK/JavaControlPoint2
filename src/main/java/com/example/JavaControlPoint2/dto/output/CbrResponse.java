package com.example.JavaControlPoint2.dto.output;

import java.math.BigDecimal;
import java.util.Map;

public record CbrResponse(
        String disclaimer,
        String date,
        long timestamp,
        String base,
        Map<String, BigDecimal>rates
) {
}

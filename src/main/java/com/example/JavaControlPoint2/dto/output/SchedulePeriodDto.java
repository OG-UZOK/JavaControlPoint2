package com.example.JavaControlPoint2.dto.output;

public record SchedulePeriodDto(
         String id,
         ScheduleSlotDto slot,
         String executorId
) {
}
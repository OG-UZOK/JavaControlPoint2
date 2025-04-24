package com.example.JavaControlPoint2.dto.output;

import java.time.LocalTime;

public record ScheduleSlotDto(
        LocalTime beginTime,
        LocalTime endTime
) {
}
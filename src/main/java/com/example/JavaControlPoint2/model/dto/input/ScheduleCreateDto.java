package com.example.JavaControlPoint2.model.dto.input;

import jakarta.validation.constraints.Size;

public record ScheduleCreateDto(
        @Size(max = 255, message = "Название расписания не должно превышать 255 символов")
        String scheduleName
) {}
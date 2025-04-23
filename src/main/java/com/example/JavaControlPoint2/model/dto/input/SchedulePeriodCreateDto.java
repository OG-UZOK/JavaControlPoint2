package com.example.JavaControlPoint2.model.dto.input;

import com.example.JavaControlPoint2.model.enumeration.SlotType;
import jakarta.validation.constraints.NotNull;

public record SchedulePeriodCreateDto(
        @NotNull(message = "Идентификатор слота обязателен")
        String slotId,

        @NotNull(message = "Идентификатор расписания обязателен")
        String scheduleId,

        @NotNull(message = "Тип слота обязателен")
        SlotType slotType,

        String executorId
) {}
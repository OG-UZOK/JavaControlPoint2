package com.example.JavaControlPoint2.model.dto.input;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ScheduleSlotCreateDto(
        @NotNull(message = "Идентификатор шаблона расписания обязателен")
        String scheduleTemplateId,

        @NotNull(message = "Время начала обязательно")
        LocalTime beginTime,

        @NotNull(message = "Время завершения обязательно")
        LocalTime endTime
) {}
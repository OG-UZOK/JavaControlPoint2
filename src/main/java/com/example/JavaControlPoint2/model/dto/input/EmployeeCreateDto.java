package com.example.JavaControlPoint2.model.dto.input;

import com.example.JavaControlPoint2.model.enumeration.EmployeePosition;
import com.example.JavaControlPoint2.model.enumeration.EmployeeStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeCreateDto(
        @NotNull(message = "Имя сотрудника обязательно")
        @Size(max = 255, message = "Имя сотрудника не должно превышать 255 символов")
        String employeeName,

        @NotNull(message = "Статус сотрудника обязателен")
        EmployeeStatus status,

        @NotNull(message = "Позиция сотрудника обязательна")
        EmployeePosition position
) {}
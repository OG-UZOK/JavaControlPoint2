package com.example.JavaControlPoint2.model.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ScheduleTemplateCreateDto(
        @Size(min = 2, max = 2, message = "Тип шаблона должен состоять из 2 символов")
        @NotNull
        @Pattern(regexp = "[A-Z]{2}", message = "Тип шаблона должен состоять из двух заглавных букв")
        String templateType
) {}
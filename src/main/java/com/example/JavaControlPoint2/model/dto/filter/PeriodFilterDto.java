package com.example.JavaControlPoint2.model.dto.filter;

import com.example.JavaControlPoint2.model.enumeration.SlotType;
import lombok.Data;

@Data
public class PeriodFilterDto {
    private String id;
    private String slotId;
    private String scheduleId;
    private SlotType slotType;
    private String administratorId;
    private String executorId;
}


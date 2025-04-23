package com.example.JavaControlPoint2.model.dto.output;

import com.example.JavaControlPoint2.model.enumeration.SlotType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class PeriodDto {
    private String id;
    private String slotId;
    private SlotType slotType;
    private String administratorId;
    private String executorId;
    private LocalTime beginTime;
    private LocalTime endTime;
}

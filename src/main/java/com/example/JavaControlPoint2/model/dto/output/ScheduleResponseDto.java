package com.example.JavaControlPoint2.model.dto.output;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ScheduleResponseDto {
    private String id;
    private String scheduleName;
    private ZonedDateTime creationDate;
    private ZonedDateTime updateDate;
    private List<PeriodDto> periods;
}


package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.model.dto.input.ScheduleCreateDto;
import com.example.JavaControlPoint2.entity.Schedule;
import com.example.JavaControlPoint2.model.dto.output.ScheduleResponseDto;
import com.example.JavaControlPoint2.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public Schedule createSchedule(@Valid @RequestBody ScheduleCreateDto scheduleCreateDto) {
        return scheduleService.createSchedule(scheduleCreateDto);
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable String id) {
        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/full")
    public ScheduleResponseDto getFullSchedule(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String scheduleName
    ) {
        return scheduleService.getFullSchedule(id, scheduleName);
    }
}
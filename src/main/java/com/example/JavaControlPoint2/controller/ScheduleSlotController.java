package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.model.dto.input.ScheduleSlotCreateDto;
import com.example.JavaControlPoint2.entity.ScheduleSlot;
import com.example.JavaControlPoint2.service.ScheduleSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-slots")
public class ScheduleSlotController {

    private final ScheduleSlotService scheduleSlotService;

    @PostMapping
    public ScheduleSlot createScheduleSlot(@Valid @RequestBody ScheduleSlotCreateDto scheduleSlotCreateDto) {
        return scheduleSlotService.createScheduleSlot(scheduleSlotCreateDto);
    }

    @GetMapping("/{id}")
    public ScheduleSlot getScheduleSlotById(@PathVariable String id) {
        return scheduleSlotService.getScheduleSlotById(id);
    }
}
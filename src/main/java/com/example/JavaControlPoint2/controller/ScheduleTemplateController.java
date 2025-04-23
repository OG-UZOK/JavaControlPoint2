package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.entity.ScheduleTemplate;
import com.example.JavaControlPoint2.model.dto.input.ScheduleTemplateCreateDto;
import com.example.JavaControlPoint2.service.ScheduleTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-templates")
public class ScheduleTemplateController {

    private final ScheduleTemplateService scheduleTemplateService;

    @PostMapping
    public ScheduleTemplate createScheduleTemplate(@Valid @RequestBody ScheduleTemplateCreateDto scheduleTemplateCreateDto) {
        return scheduleTemplateService.createScheduleTemplate(scheduleTemplateCreateDto);
    }

    @GetMapping("/{id}")
    public ScheduleTemplate getScheduleTemplateById(@PathVariable String id) {
        return scheduleTemplateService.getScheduleTemplateById(id);
    }
}

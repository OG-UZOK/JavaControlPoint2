package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.model.dto.filter.PeriodFilterDto;
import com.example.JavaControlPoint2.model.dto.filter.SortDto;
import com.example.JavaControlPoint2.model.dto.input.SchedulePeriodCreateDto;
import com.example.JavaControlPoint2.entity.SchedulePeriod;
import com.example.JavaControlPoint2.model.enumeration.SlotType;
import com.example.JavaControlPoint2.service.SchedulePeriodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-periods")
public class SchedulePeriodController {

    private final SchedulePeriodService schedulePeriodService;

    @PostMapping
    public SchedulePeriod createSchedulePeriod(@Valid @RequestBody SchedulePeriodCreateDto schedulePeriodCreateDto,
                                               @RequestHeader("x-current-user") String currentUserId) {
        if (currentUserId == null || currentUserId.isBlank()) {
            throw new IllegalArgumentException("Заголовок x-current-user обязателен");
        }

        return schedulePeriodService.createSchedulePeriod(schedulePeriodCreateDto, currentUserId);
    }

    @GetMapping("/{id}")
    public SchedulePeriod getSchedulePeriodById(@PathVariable String id) {
        return schedulePeriodService.getSchedulePeriodById(id);
    }

    @GetMapping("/filtered")
    public Page<SchedulePeriod> getFilteredPeriods(
            @RequestParam(required = false) Map<String, String> filter,
            @RequestParam(required = false) Map<String, String> sort,
            @RequestParam(defaultValue = "0") int page
    ) {
        PeriodFilterDto filterDto = new PeriodFilterDto();
        if (filter != null) {
            filterDto.setId(filter.get("id"));
            filterDto.setSlotId(filter.get("slotId"));
            filterDto.setScheduleId(filter.get("scheduleId"));
            filterDto.setSlotType(SlotType.valueOf(filter.get("slotType")));
            filterDto.setAdministratorId(filter.get("administratorId"));
            filterDto.setExecutorId(filter.get("executorId"));
        }

        SortDto sortDto = new SortDto();
        if (sort != null) {
            sortDto.setField(sort.get("field"));
            sortDto.setDirection(sort.get("direction"));
        }

        return schedulePeriodService.getFilteredPeriods(filterDto, sortDto, page);
    }
}
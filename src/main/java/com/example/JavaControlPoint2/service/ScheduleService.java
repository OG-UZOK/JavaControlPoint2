package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.entity.Schedule;
import com.example.JavaControlPoint2.entity.SchedulePeriod;
import com.example.JavaControlPoint2.model.dto.input.ScheduleCreateDto;
import com.example.JavaControlPoint2.model.dto.output.PeriodDto;
import com.example.JavaControlPoint2.model.dto.output.ScheduleResponseDto;
import com.example.JavaControlPoint2.repository.SchedulePeriodRepository;
import com.example.JavaControlPoint2.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SchedulePeriodRepository schedulePeriodRepository;

    public Schedule createSchedule(ScheduleCreateDto scheduleCreateDto) {
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID().toString().replace("-", ""));
        schedule.setScheduleName(scheduleCreateDto.scheduleName());
        schedule.setCreationDate(ZonedDateTime.now());
        schedule.setUpdateDate(ZonedDateTime.now());
        return scheduleRepository.save(schedule);
    }


    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Расписание с id " + id + " не найдено"));
    }

    public ScheduleResponseDto getFullSchedule(String id, String scheduleName) {
        if (id == null && scheduleName == null) {
            throw new IllegalArgumentException("Необходимо указать id или scheduleName");
        }

        Schedule schedule = id != null
                ? scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Расписание с id " + id + " не найдено"))
                : scheduleRepository.findByScheduleName(scheduleName)
                .orElseThrow(() -> new RuntimeException("Расписание с именем " + scheduleName + " не найдено"));

        List<SchedulePeriod> periods = schedulePeriodRepository.findPeriodsByScheduleIdOrderedByBeginTime(schedule.getId());

        List<PeriodDto> periodDtos = periods.stream()
                .map(period -> new PeriodDto(
                        period.getId(),
                        period.getSlot().getId(),
                        period.getSlotType(),
                        period.getAdministratorId(),
                        period.getExecutorId(),
                        period.getSlot().getBeginTime(),
                        period.getSlot().getEndTime()
                ))
                .collect(Collectors.toList());

        // Создаем ответ
        ScheduleResponseDto response = new ScheduleResponseDto();
        response.setId(schedule.getId());
        response.setScheduleName(schedule.getScheduleName());
        response.setCreationDate(schedule.getCreationDate());
        response.setUpdateDate(schedule.getUpdateDate());
        response.setPeriods(periodDtos);

        return response;
    }
}
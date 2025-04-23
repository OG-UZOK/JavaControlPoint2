package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.model.dto.input.ScheduleSlotCreateDto;
import com.example.JavaControlPoint2.entity.ScheduleSlot;
import com.example.JavaControlPoint2.repository.ScheduleSlotRepository;
import com.example.JavaControlPoint2.repository.ScheduleTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleSlotService {

    private final ScheduleSlotRepository scheduleSlotRepository;
    private final ScheduleTemplateRepository scheduleTemplateRepository;

    public ScheduleSlot createScheduleSlot(ScheduleSlotCreateDto scheduleSlotCreateDto) {
        ScheduleSlot scheduleSlot = new ScheduleSlot();
        scheduleSlot.setId(UUID.randomUUID().toString().replace("-", ""));
        scheduleSlot.setScheduleTemplate(
                scheduleTemplateRepository.findById(scheduleSlotCreateDto.scheduleTemplateId())
                        .orElseThrow(() -> new RuntimeException("Шаблон расписания не найден"))
        );

        if (!scheduleSlotCreateDto.beginTime().isBefore(scheduleSlotCreateDto.endTime())) {
            throw new RuntimeException("the begin time cannot be later than the end time");
        }

        scheduleSlot.setBeginTime(scheduleSlotCreateDto.beginTime());
        scheduleSlot.setEndTime(scheduleSlotCreateDto.endTime());
        return scheduleSlotRepository.save(scheduleSlot);
    }

    public ScheduleSlot getScheduleSlotById(String id) {
        return scheduleSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Слот расписания с id " + id + " не найден"));
    }
}
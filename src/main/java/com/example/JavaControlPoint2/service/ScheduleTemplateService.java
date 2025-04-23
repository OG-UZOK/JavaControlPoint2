package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.model.dto.input.ScheduleTemplateCreateDto;
import com.example.JavaControlPoint2.entity.ScheduleTemplate;
import com.example.JavaControlPoint2.repository.ScheduleTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleTemplateService {

    private final ScheduleTemplateRepository scheduleTemplateRepository;

    public ScheduleTemplate createScheduleTemplate(ScheduleTemplateCreateDto scheduleTemplateCreateDto) {
        ScheduleTemplate scheduleTemplate = new ScheduleTemplate();
        scheduleTemplate.setId(UUID.randomUUID().toString().replace("-", ""));
        scheduleTemplate.setCreationDate(ZonedDateTime.now());
        scheduleTemplate.setTemplateType(scheduleTemplateCreateDto.templateType());
        return scheduleTemplateRepository.save(scheduleTemplate);
    }

    public ScheduleTemplate getScheduleTemplateById(String id) {
        return scheduleTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Шаблон расписания с id " + id + " не найден"));
    }
}
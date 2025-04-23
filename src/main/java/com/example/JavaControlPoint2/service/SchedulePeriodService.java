package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.entity.ScheduleSlot;
import com.example.JavaControlPoint2.model.dto.filter.PeriodFilterDto;
import com.example.JavaControlPoint2.model.dto.filter.SortDto;
import com.example.JavaControlPoint2.model.dto.input.SchedulePeriodCreateDto;
import com.example.JavaControlPoint2.entity.SchedulePeriod;
import com.example.JavaControlPoint2.repository.EmployeeRepository;
import com.example.JavaControlPoint2.repository.SchedulePeriodRepository;
import com.example.JavaControlPoint2.repository.ScheduleRepository;
import com.example.JavaControlPoint2.repository.ScheduleSlotRepository;
import com.example.JavaControlPoint2.specification.SchedulePeriodSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchedulePeriodService {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final ScheduleSlotRepository scheduleSlotRepository;
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    public SchedulePeriod createSchedulePeriod(SchedulePeriodCreateDto schedulePeriodCreateDto, String currentUserId) {
        SchedulePeriod schedulePeriod = new SchedulePeriod();
        schedulePeriod.setId(UUID.randomUUID().toString().replace("-", ""));
        schedulePeriod.setSlot(
                scheduleSlotRepository.findById(schedulePeriodCreateDto.slotId())
                        .orElseThrow(() -> new RuntimeException("Слот не найден"))
        );
        schedulePeriod.setSchedule(
                scheduleRepository.findById(schedulePeriodCreateDto.scheduleId())
                        .orElseThrow(() -> new RuntimeException("Расписание не найдено"))
        );
        schedulePeriod.setAdministratorId(
                employeeRepository.findById(currentUserId)
                        .orElseThrow(() -> new RuntimeException("Текущий пользователь не найден")).getId()
        );
        schedulePeriod.setSlotType(schedulePeriodCreateDto.slotType());
        schedulePeriod.setAdministratorId(currentUserId);

        if (!Objects.equals(currentUserId, schedulePeriodCreateDto.executorId())) {
            schedulePeriod.setExecutorId(
                    employeeRepository.findById(schedulePeriodCreateDto.executorId())
                            .orElseThrow(() -> new RuntimeException("Исполнитель не найден")).getId()
            );
        }

        if (isPeriodOverlapping(schedulePeriod)) {
            throw new IllegalArgumentException("Новый период пересекается с существующими периодами");
        }

        return schedulePeriodRepository.save(schedulePeriod);
    }

    private boolean isPeriodOverlapping(SchedulePeriod newPeriod) {
        List<SchedulePeriod> existingPeriods = schedulePeriodRepository
                .findByScheduleIdAndSlotId(newPeriod.getSchedule().getId());

        for (SchedulePeriod existingPeriod : existingPeriods) {
            if (isOverlapping(existingPeriod.getSlot(), newPeriod.getSlot())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOverlapping(ScheduleSlot existingSlot, ScheduleSlot newPeriod) {
        return !existingSlot.getEndTime().isBefore(newPeriod.getBeginTime()) &&
                !existingSlot.getBeginTime().isAfter(newPeriod.getEndTime());
    }

    public SchedulePeriod getSchedulePeriodById(String id) {
        return schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Период расписания с id " + id + " не найден"));
    }

    public Page<SchedulePeriod> getFilteredPeriods(PeriodFilterDto filter, SortDto sort, int page) {
        // Создаем спецификацию для фильтрации
        var specification = SchedulePeriodSpecifications.withFilter(filter);

        // Создаем объект сортировки
        var sortDirection = sort != null && "DESC".equalsIgnoreCase(sort.getDirection())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        var sortField = sort != null ? sort.getField() : "id";
        var pageable = PageRequest.of(page, 10, Sort.by(sortDirection, sortField));

        // Выполняем запрос с фильтрацией и сортировкой
        return schedulePeriodRepository.findAll(specification, pageable);
    }
}
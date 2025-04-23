package com.example.JavaControlPoint2.specification;

import com.example.JavaControlPoint2.entity.SchedulePeriod;
import com.example.JavaControlPoint2.model.dto.filter.PeriodFilterDto;
import org.springframework.data.jpa.domain.Specification;

public class SchedulePeriodSpecifications {

    public static Specification<SchedulePeriod> withFilter(PeriodFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }

            var predicate = criteriaBuilder.conjunction();

            if (filter.getId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), filter.getId()));
            }
            if (filter.getSlotId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("slot").get("id"), filter.getSlotId()));
            }
            if (filter.getScheduleId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("schedule").get("id"), filter.getScheduleId()));
            }
            if (filter.getSlotType() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("slotType"), filter.getSlotType()));
            }
            if (filter.getAdministratorId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("administratorId"), filter.getAdministratorId()));
            }
            if (filter.getExecutorId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("executorId"), filter.getExecutorId()));
            }

            return predicate;
        };
    }
}
package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.ScheduleSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, String> {
}


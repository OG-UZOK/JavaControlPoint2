package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.SchedulePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, String>, JpaSpecificationExecutor<SchedulePeriod> {
    @Query(value = "SELECT * FROM schedule_period WHERE schedule_id = :scheduleId", nativeQuery = true)
    List<SchedulePeriod> findByScheduleIdAndSlotId(@Param("scheduleId") String scheduleId);

    @Query("SELECT sp FROM SchedulePeriod sp " +
            "JOIN FETCH sp.slot s " +
            "WHERE sp.schedule.id = :scheduleId " +
            "ORDER BY s.beginTime")
    List<SchedulePeriod> findPeriodsByScheduleIdOrderedByBeginTime(String scheduleId);
}


package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    @Query(value = "SELECT * FROM schedule WHERE schedule_name = :scheduleName", nativeQuery = true)
    Optional<Schedule> findByScheduleName(@Param("scheduleName") String scheduleName);
}


package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    @Query("SELECT SUM(r.hours) FROM Report r WHERE r.employee.id = :employeeId")
    Double findTotalHoursByEmployeeId(@Param("employeeId") UUID employeeId);
}

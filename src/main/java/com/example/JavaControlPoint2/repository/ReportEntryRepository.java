package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.ReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReportEntryRepository extends JpaRepository<ReportEntry, UUID> {

    @Query("SELECT SUM(r.hours) FROM ReportEntry r WHERE r.employee.id = :employeeId")
    Double findTotalHoursByEmployeeId(@Param("employeeId") UUID employeeId);
    @Modifying
    @Query("DELETE FROM ReportEntry re WHERE re.report.id = :reportId")
    void deleteByReportId(UUID reportId);
}

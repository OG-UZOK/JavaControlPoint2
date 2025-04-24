package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.entries WHERE r.id = :id")
    Optional<Report> findByIdWithEntries(@Param("id") UUID id);
}

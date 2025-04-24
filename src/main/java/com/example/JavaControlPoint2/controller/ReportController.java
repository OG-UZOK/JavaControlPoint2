package com.example.JavaControlPoint2.controller;

import com.example.JavaControlPoint2.dto.output.EmployeeOutput;
import com.example.JavaControlPoint2.dto.output.EmployeesId;
import com.example.JavaControlPoint2.dto.output.ReportCurrencyOutput;
import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @GetMapping
    public ResponseEntity<List<ReportsOutput>> getReports() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportCurrencyOutput> getReportByIdInCurrency(@PathVariable("id") UUID id, @RequestParam(value = "currency", required = false) String currency) {
        return ResponseEntity.ok(reportService.getReportByIdInCurrency(id, currency));
    }

}

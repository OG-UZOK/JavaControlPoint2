package com.example.JavaControlPoint2.mapper;

import com.example.JavaControlPoint2.dto.output.ReportCurrencyOutput;
import com.example.JavaControlPoint2.dto.output.ReportEntryCurrencyOutput;
import com.example.JavaControlPoint2.entity.Report;
import com.example.JavaControlPoint2.entity.ReportEntry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ReportMapper {

    public ReportCurrencyOutput toCurrencyOutput(Report report, String currency, BigDecimal rate) {
        List<ReportEntryCurrencyOutput> entries = report.getEntries().stream()
                .map(entry -> convertEntry(entry, currency, rate))
                .toList();

        return new ReportCurrencyOutput(
                report.getId(),
                report.getStartDate(),
                report.getEndDate(),
                entries
        );
    }

    private ReportEntryCurrencyOutput convertEntry(ReportEntry entry, String currency, BigDecimal rate) {
        double convertedSalary = entry.getSalary() * rate.doubleValue();

        return new ReportEntryCurrencyOutput(
                entry.getEmployee().getId(),
                entry.getHours(),
                currency,
                convertedSalary
        );
    }
}
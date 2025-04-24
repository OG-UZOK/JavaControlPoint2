package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.dto.output.ReportCurrencyOutput;
import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.entity.ExchangeRate;
import com.example.JavaControlPoint2.entity.Report;
import com.example.JavaControlPoint2.exception.NotFoundException;
import com.example.JavaControlPoint2.mapper.ReportMapper;
import com.example.JavaControlPoint2.repository.ExchangeRateRepository;
import com.example.JavaControlPoint2.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ReportMapper reportMapper;

    public List<ReportsOutput> getReports() {
        List<Report> reportList = reportRepository.findAll();
        List<ReportsOutput> reportsOutputs = new ArrayList<>();
        for (Report report: reportList) {
            reportsOutputs.add(new ReportsOutput(report.getId(), report.getStartDate(), report.getEndDate()));
        }
        return reportsOutputs;
    }

    @SneakyThrows
    public ReportCurrencyOutput getReportByIdInCurrency(UUID id, String targetCurrency) {
        Report report = reportRepository.findByIdWithEntries(id)
                .orElseThrow(() -> new NotFoundException("Incorrect id"));

        boolean useRub = targetCurrency == null || targetCurrency.equals("RUB");
        targetCurrency = useRub ? "RUB" : targetCurrency;

        BigDecimal rate = useRub
                ? BigDecimal.ONE
                : exchangeRateRepository.findByCurrency(targetCurrency)
                .map(ExchangeRate::getRate)
                .orElseThrow(() -> new NotFoundException("Currency not supported"));

        return reportMapper.toCurrencyOutput(report, targetCurrency, rate);
    }
}

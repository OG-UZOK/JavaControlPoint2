package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.dto.output.ReportCurrencyOutput;
import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.entity.ExchangeRate;
import com.example.JavaControlPoint2.entity.Report;
import com.example.JavaControlPoint2.exception.NotFoundException;
import com.example.JavaControlPoint2.repository.ExchangeRateRepository;
import com.example.JavaControlPoint2.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public List<ReportsOutput> getReports() {
        return reportRepository.findAllReports();
    }

    @SneakyThrows
    public ReportCurrencyOutput getReportByIdInCurrency(UUID id, String currency) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incorrect id"));

        if (currency == null || currency.equals("RUB")) {
            currency = "RUB";
            return new ReportCurrencyOutput(
                    report.getId(), report.getStartDate(), report.getEndDate(), report.getEmployee().getId(), report.getHours(), currency, report.getSalary());
        }

        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(currency)
                .orElseThrow(() -> new NotFoundException("Currency does not exist"));

        return new ReportCurrencyOutput(
                report.getId(), report.getStartDate(), report.getEndDate(), report.getEmployee().getId(), report.getHours(), currency, report.getSalary() * exchangeRate.getRate().doubleValue());
    }
}

package com.example.JavaControlPoint2.service;

import com.example.JavaControlPoint2.config.KafkaProducer;
import com.example.JavaControlPoint2.dto.input.KafkaMessage;
import com.example.JavaControlPoint2.dto.output.ReportCurrencyOutput;
import com.example.JavaControlPoint2.dto.output.ReportsOutput;
import com.example.JavaControlPoint2.dto.output.SchedulePeriodDto;
import com.example.JavaControlPoint2.entity.Employee;
import com.example.JavaControlPoint2.entity.ExchangeRate;
import com.example.JavaControlPoint2.entity.Report;
import com.example.JavaControlPoint2.entity.ReportEntry;
import com.example.JavaControlPoint2.exception.NotFoundException;
import com.example.JavaControlPoint2.mapper.ReportMapper;
import com.example.JavaControlPoint2.repository.EmployeeRepository;
import com.example.JavaControlPoint2.repository.ExchangeRateRepository;
import com.example.JavaControlPoint2.repository.ReportEntryRepository;
import com.example.JavaControlPoint2.repository.ReportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ReportMapper reportMapper;
    private final RestTemplate restTemplate;
    private final EmployeeRepository employeeRepository;
    private final ReportEntryRepository reportEntryRepository;
    private final KafkaProducer kafkaProducer;

    @Value("${scheduler.service.url}")
    private String scheduleServiceUrl;

    @Value("${report.base-hourly-rate}")
    private Integer baseHourlySalary;

    @Value("${topic.reports}")
    private String reportTopic;

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



    @Transactional
    public void createReport() throws JsonProcessingException {
        Report lastReport = reportRepository.findTopByOrderByStartDateDesc()
                .orElseGet(() -> createInitialReport());

        List<SchedulePeriodDto> periods = fetchSchedulePeriods();

        LocalDate newReportDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(lastReport.getStartDate(), newReportDate);

        lastReport.setEndDate(newReportDate.minusDays(1));
        reportRepository.save(lastReport);

        Report newReport = new Report();
        newReport.setStartDate(newReportDate);
        newReport = reportRepository.save(newReport);

        Map<String, Double> employeeHoursPerDay = calculateEmployeeHours(periods);
        createReportEntries(newReport, employeeHoursPerDay, daysBetween);

        kafkaProducer.sendJsonMessage(reportTopic, new KafkaMessage(newReport.getId().toString()));
    }

    private List<SchedulePeriodDto> fetchSchedulePeriods() {
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("executorId", "notNull");

        ResponseEntity<PageImpl<SchedulePeriodDto>> response = restTemplate.exchange(
                scheduleServiceUrl + "?page=0&size=1000",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<PageImpl<SchedulePeriodDto>>() {},
                filterParams);

        return response.getBody().getContent();
    }

    private Map<String, Double> calculateEmployeeHours(List<SchedulePeriodDto> periods) {
        Map<String, Double> employeeHours = new HashMap<>();

        for (SchedulePeriodDto period : periods) {
            if (period.executorId() != null) {
                Duration duration = Duration.between(
                        period.slot().beginTime(),
                        period.slot().endTime()
                );
                double hours = duration.toMinutes() / 60.0;

                employeeHours.merge(
                        period.executorId(),
                        hours,
                        Double::sum
                );
            }
        }

        return employeeHours;
    }

    private void createReportEntries(Report report,
                                     Map<String, Double> employeeHoursPerDay,
                                     long daysBetween) {

        for (Map.Entry<String, Double> entry : employeeHoursPerDay.entrySet()) {
            String employeeId = entry.getKey();
            double dailyHours = entry.getValue();
            double totalHours = dailyHours * daysBetween;

            Employee employee = employeeRepository.findById(UUID.fromString(employeeId))
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

            double salaryCoefficient = employee.getRole().getCoefficient();
            double salary = totalHours * salaryCoefficient * baseHourlySalary;

            ReportEntry reportEntry = new ReportEntry();
            reportEntry.setReport(report);
            reportEntry.setEmployee(employee);
            reportEntry.setHours(totalHours);
            reportEntry.setSalary(salary);

            reportEntryRepository.save(reportEntry);
        }
    }

    private Report createInitialReport() {
        Report report = new Report();
        report.setStartDate(LocalDate.now().minusDays(1));
        return reportRepository.save(report);
    }

    @Transactional
    @SneakyThrows
    public Report refreshReport(UUID reportId) {
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        List<SchedulePeriodDto> periods = fetchSchedulePeriods();
        Map<String, Double> employeeHoursPerDay = calculateEmployeeHours(periods);

        reportEntryRepository.deleteByReportId(reportId);

        long daysBetween = ChronoUnit.DAYS.between(
                existingReport.getStartDate(),
                existingReport.getEndDate() != null
                        ? existingReport.getEndDate()
                        : LocalDate.now()
        ) + 1;

        createReportEntries(existingReport, employeeHoursPerDay, daysBetween);

        return existingReport;
    }
}

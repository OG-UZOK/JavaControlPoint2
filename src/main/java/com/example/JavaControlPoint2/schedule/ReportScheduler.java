package com.example.JavaControlPoint2.schedule;

import com.example.JavaControlPoint2.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportScheduler {
    private final ReportService reportService;
    @Scheduled(cron = "${scheduler.service.period}")
    public void createReport() throws JsonProcessingException {
        reportService.createReport();
    }
}

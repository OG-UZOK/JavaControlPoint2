package com.example.JavaControlPoint2.schedule;

import com.example.JavaControlPoint2.dto.output.CbrResponse;
import com.example.JavaControlPoint2.entity.ExchangeRate;
import com.example.JavaControlPoint2.repository.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ExchangeRateUpdater {
    private final ExchangeRateRepository repository;
    private final RestTemplate restTemplate;

    @Scheduled(fixedDelay = 60_000)
    public void updateRates() {
        String apiUrl = "https://www.cbr-xml-daily.ru/latest.js";
        CbrResponse response = restTemplate.getForObject(apiUrl, CbrResponse.class);

        if (response != null) {
            response.rates().forEach((currency, rate) -> {
                ExchangeRate rateEntity = repository.findByBaseCurrencyAndCurrency("RUB", currency)
                        .orElse(new ExchangeRate());

                rateEntity.setBaseCurrency("RUB");
                rateEntity.setCurrency(currency);
                rateEntity.setRate(rate);
                rateEntity.setTimestamp(LocalDateTime.now());

                repository.save(rateEntity);
            });
        }
    }
}

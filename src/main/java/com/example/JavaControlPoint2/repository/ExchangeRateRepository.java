package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    Optional<ExchangeRate> findByBaseCurrencyAndCurrency(String rub, String currency);

    Optional<ExchangeRate> findByCurrency(String currency);
}

package com.example.JavaControlPoint2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 3)
    private String baseCurrency;

    @Column(length = 3)
    private String currency;

    @Column(precision = 19, scale = 6)
    private BigDecimal rate;

    @UpdateTimestamp
    private LocalDateTime timestamp;
}
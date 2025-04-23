package com.example.JavaControlPoint2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "schedule_template")
public class ScheduleTemplate {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    @Column(name = "template_type", length = 2, nullable = false)
    private String templateType;
}
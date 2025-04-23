package com.example.JavaControlPoint2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "schedule_name", length = 255)
    private String scheduleName;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private ZonedDateTime updateDate;
}
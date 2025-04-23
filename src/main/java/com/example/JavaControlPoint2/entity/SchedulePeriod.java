package com.example.JavaControlPoint2.entity;

import com.example.JavaControlPoint2.model.enumeration.SlotType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "schedule_period")
public class SchedulePeriod {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private ScheduleSlot slot;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", length = 20, nullable = false)
    private SlotType slotType;

    @Column(name = "administrator_id", length = 32, nullable = false)
    private String administratorId;

    @Column(name = "executor_id", length = 32)
    private String executorId;
}
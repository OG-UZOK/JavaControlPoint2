package com.example.JavaControlPoint2.entity;

import com.example.JavaControlPoint2.model.enumeration.EmployeePosition;
import com.example.JavaControlPoint2.model.enumeration.EmployeeStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "employee_name", length = 255, nullable = false)
    private String employeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private EmployeeStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", length = 20, nullable = false)
    private EmployeePosition position;
}
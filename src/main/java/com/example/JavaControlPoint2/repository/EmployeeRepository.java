package com.example.JavaControlPoint2.repository;

import com.example.JavaControlPoint2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query("SELECT e.id FROM Employee e")
    List<UUID> findAllEmployeesId();
}

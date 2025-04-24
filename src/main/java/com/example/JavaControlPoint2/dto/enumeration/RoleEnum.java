package com.example.JavaControlPoint2.dto.enumeration;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    SYSADMIN(1.2),
    MANAGER(1.5),
    WORKER(1);

    private final double coefficient;
}

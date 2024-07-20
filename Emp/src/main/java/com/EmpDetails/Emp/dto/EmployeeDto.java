package com.EmpDetails.Emp.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class EmployeeDto {
    private Long id;
    private String name;
    private LocalDate dateOfJoining;
    private boolean isactive;
    public EmployeeDto() {
        // No-argument constructor
    }


}

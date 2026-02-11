package com.gb.Viewhr.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {

    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean halfDay;
    private String reason;
}
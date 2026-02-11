package com.gb.Viewhr.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean halfDay;
    private String reason;

    private String status; // PENDING, APPROVED, REJECTED, CANCELLED

    private LocalDateTime appliedAt;
    private LocalDateTime decisionAt;

    @ManyToOne
    private User decidedBy;

    private String remarks;
}
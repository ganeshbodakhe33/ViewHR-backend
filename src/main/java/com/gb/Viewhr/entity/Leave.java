package com.gb.Viewhr.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User employee;

    @ManyToOne
    private User manager;

    @ManyToOne
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean halfDay;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @Column(length = 1000)
    private String reason;
}
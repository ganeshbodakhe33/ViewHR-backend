package com.gb.Viewhr.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private int yearlyLimit;

    private boolean allowCarryForward;

    private int maxCarryForward;

    private boolean isActive = true;
}
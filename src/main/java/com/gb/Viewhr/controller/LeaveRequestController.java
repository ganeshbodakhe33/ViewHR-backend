package com.gb.Viewhr.controller;


import com.gb.Viewhr.dto.LeaveRequestDTO;
import com.gb.Viewhr.entity.LeaveRequest;
import com.gb.Viewhr.service.LeaveRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveRequestController {

    private final LeaveRequestService service;

    public LeaveRequestController(LeaveRequestService service) {
        this.service = service;
    }

    @PostMapping
    public LeaveRequest apply(Authentication authentication,
                              @RequestBody LeaveRequestDTO dto) {
        return service.applyLeave(authentication.getName(), dto);
    }

    @GetMapping("/me")
    public List<LeaveRequest> myLeaves(Authentication authentication) {
        return service.getMyLeaves(authentication.getName());
    }

    @GetMapping("/pending")
    public List<LeaveRequest> pending(Authentication authentication) {
        // Manager email -> get managerId
        // For now, service will fetch via manager id externally if needed
        return service.getPendingApprovals(
                Long.parseLong(authentication.getName())
        );
    }

    @PutMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id,
                                Authentication authentication) {
        return service.approve(id, authentication.getName());
    }

    @PutMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id,
                               Authentication authentication,
                               @RequestParam String remarks) {
        return service.reject(id, authentication.getName(), remarks);
    }

    @PutMapping("/{id}/cancel")
    public LeaveRequest cancel(@PathVariable Long id,
                               Authentication authentication) {
        return service.cancel(id, authentication.getName());
    }
}
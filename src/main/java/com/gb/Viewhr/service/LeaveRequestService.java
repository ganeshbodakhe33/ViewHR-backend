package com.gb.Viewhr.service;


import com.gb.Viewhr.dto.LeaveRequestDTO;
import com.gb.Viewhr.entity.LeaveRequest;
import com.gb.Viewhr.entity.LeaveType;
import com.gb.Viewhr.entity.User;
import com.gb.Viewhr.repository.LeaveRequestRepository;
import com.gb.Viewhr.repository.LeaveTypeRepository;
import com.gb.Viewhr.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository,
                               UserRepository userRepository,
                               LeaveTypeRepository leaveTypeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public LeaveRequest applyLeave(String email, LeaveRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .user(user)
                .leaveType(leaveType)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .halfDay(dto.isHalfDay())
                .reason(dto.getReason())
                .status("PENDING")
                .appliedAt(LocalDateTime.now())
                .build();

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getMyLeaves(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return leaveRequestRepository.findByUser(user);
    }

    public List<LeaveRequest> getPendingApprovals(Long managerId) {
        return leaveRequestRepository
                .findByUser_Manager_IdAndStatus(managerId, "PENDING");
    }

    public LeaveRequest approve(Long requestId, String managerEmail) {

        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        request.setStatus("APPROVED");
        request.setDecisionAt(LocalDateTime.now());
        request.setDecidedBy(manager);

        return leaveRequestRepository.save(request);
    }

    public LeaveRequest reject(Long requestId, String managerEmail, String remarks) {

        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User manager = userRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        request.setStatus("REJECTED");
        request.setDecisionAt(LocalDateTime.now());
        request.setDecidedBy(manager);
        request.setRemarks(remarks);

        return leaveRequestRepository.save(request);
    }

    public LeaveRequest cancel(Long requestId, String email) {

        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to cancel this leave");
        }

        request.setStatus("CANCELLED");
        return leaveRequestRepository.save(request);
    }
}
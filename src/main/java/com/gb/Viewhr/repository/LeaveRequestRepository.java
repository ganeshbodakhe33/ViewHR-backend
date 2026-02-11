package com.gb.Viewhr.repository;


import com.gb.Viewhr.entity.LeaveRequest;
import com.gb.Viewhr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByUser(User user);

    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByUser_Manager_IdAndStatus(Long managerId, String status);
}
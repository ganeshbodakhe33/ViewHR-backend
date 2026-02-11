package com.gb.Viewhr.service;


import com.gb.Viewhr.entity.LeaveType;
import com.gb.Viewhr.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository repository;

    public LeaveTypeService(LeaveTypeRepository repository) {
        this.repository = repository;
    }

    public List<LeaveType> getAll() {
        return repository.findAll();
    }

    public LeaveType create(LeaveType leaveType) {
        return repository.save(leaveType);
    }

    public LeaveType update(Long id, LeaveType updated) {
        LeaveType existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LeaveType not found"));

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setYearlyLimit(updated.getYearlyLimit());
        existing.setAllowCarryForward(updated.isAllowCarryForward());
        existing.setMaxCarryForward(updated.getMaxCarryForward());
        existing.setActive(updated.isActive());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
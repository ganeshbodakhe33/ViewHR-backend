package com.gb.Viewhr.controller;


import com.gb.Viewhr.entity.LeaveType;
import com.gb.Viewhr.service.LeaveTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveTypeController {

    private final LeaveTypeService service;

    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeaveType> getAll() {
        return service.getAll();
    }

    @PostMapping
    public LeaveType create(@RequestBody LeaveType leaveType) {
        return service.create(leaveType);
    }

    @PutMapping("/{id}")
    public LeaveType update(@PathVariable Long id,
                            @RequestBody LeaveType leaveType) {
        return service.update(id, leaveType);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Leave type deleted";
    }
}

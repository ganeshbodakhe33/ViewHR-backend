package com.gb.Viewhr.controller;

import com.gb.Viewhr.entity.Holiday;
import com.gb.Viewhr.service.HolidayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {

    private final HolidayService service;

    public HolidayController(HolidayService service) {
        this.service = service;
    }

    @GetMapping
    public List<Holiday> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Holiday create(@RequestBody Holiday holiday) {
        return service.create(holiday);
    }

    @PutMapping("/{id}")
    public Holiday update(@PathVariable Long id,
                          @RequestBody Holiday holiday) {
        return service.update(id, holiday);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Holiday deleted";
    }
}
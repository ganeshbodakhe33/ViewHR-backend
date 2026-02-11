package com.gb.Viewhr.service;


import com.gb.Viewhr.entity.Holiday;
import com.gb.Viewhr.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    private final HolidayRepository repository;

    public HolidayService(HolidayRepository repository) {
        this.repository = repository;
    }

    public List<Holiday> getAll() {
        return repository.findAll();
    }

    public Holiday create(Holiday holiday) {
        return repository.save(holiday);
    }

    public Holiday update(Long id, Holiday updated) {
        Holiday existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        existing.setHolidayDate(updated.getHolidayDate());
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
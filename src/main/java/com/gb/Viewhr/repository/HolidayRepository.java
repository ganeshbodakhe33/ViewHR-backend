package com.gb.Viewhr.repository;

import com.gb.Viewhr.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}

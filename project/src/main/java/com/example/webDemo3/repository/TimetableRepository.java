package com.example.webDemo3.repository;

import com.example.webDemo3.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable,Long> {
}

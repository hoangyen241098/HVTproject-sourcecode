package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolRankWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
kimpt142
 */
@Repository
public interface SchoolRankWeekRepository extends JpaRepository<SchoolRankWeek,Integer> {
}

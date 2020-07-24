package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolRankYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
kimpt142 - 23/07
 */
public interface SchoolRankYearRepository extends JpaRepository<SchoolRankYear,Integer> {

    @Query(value = "select distinct srw.schoolRankYearId.YEAR_ID from SchoolRankYear srw")
    List<Integer> getAllDistinctYearId();
}

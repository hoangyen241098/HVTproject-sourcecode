package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear,Integer>{
    @Query(value = "select s from SchoolYear s order by s.fromDate desc")
    List<SchoolYear> findAllSortByFromDate();
}

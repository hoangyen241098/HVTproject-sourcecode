package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
kimpt142 - 23/07
 */
public interface SchoolMonthRepository extends JpaRepository<SchoolMonth,Integer> {
    @Query(value="select sm from SchoolMonth sm where sm.monthId <> 0")
    List<SchoolMonth> findSchoolMonthExcludeZero();
}

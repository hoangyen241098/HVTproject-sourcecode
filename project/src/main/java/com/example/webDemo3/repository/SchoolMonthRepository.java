package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
kimpt142 - 23/07
 */
public interface SchoolMonthRepository extends JpaRepository<SchoolMonth,Integer> {

    @Query(value="select sm from SchoolMonth sm where sm.monthId <> 0 and sm.yearId = :yearId")
    List<SchoolMonth> findSchoolMonthByYearIdExcludeZero(@Param("yearId") Integer yearId);

    @Query(value = "select s from SchoolMonth s where s.month = :month and s.semesterId = :semesterId and s.yearId = :yearId")
    SchoolMonth findSchoolMonthByMonthAndSemesterAndYearId(@Param("month") Integer month, @Param("semesterId") Integer semesterId, @Param("yearId") Integer yearId);

    @Query(value = "select s from SchoolMonth s where s.month = :month and s.monthId <> :monthId")
    SchoolMonth findExistByMonth(@Param("month") Integer month, @Param("monthId") Integer monthId);
}

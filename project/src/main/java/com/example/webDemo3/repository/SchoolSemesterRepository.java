package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolMonth;
import com.example.webDemo3.entity.SchoolSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
kimpt142 - 23/07
 */
public interface SchoolSemesterRepository extends JpaRepository<SchoolSemester,Integer> {
    @Query(value = "select s from SchoolSemester s where s.semester = :semester and s.yearId = :yearId")
    SchoolSemester findSchoolSemesterBySemesterAndYearId(@Param("semester") Integer semester, @Param("yearId") Integer yearId);

}

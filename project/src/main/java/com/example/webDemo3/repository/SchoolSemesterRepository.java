package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolMonth;
import com.example.webDemo3.entity.SchoolSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
kimpt142 - 23/07
 */
public interface SchoolSemesterRepository extends JpaRepository<SchoolSemester,Integer> {
    @Query(value = "select s from SchoolSemester s where s.semester = :semester and s.yearId = :yearId")
    SchoolSemester findSchoolSemesterBySemesterAndYearId(@Param("semester") Integer semester, @Param("yearId") Integer yearId);

    @Query(value="select ss from SchoolSemester ss where ss.semesterId <> 0 and ss.yearId = :yearId order by ss.createDate")
    List<SchoolSemester> findSchoolSemesterByYearIdExcludeZero(@Param("yearId") Integer yearId);

    @Query(value = "select s from SchoolSemester s where s.semester = :semester and s.semesterId <> :semesterId")
    SchoolSemester findExistBySemester(@Param("semester") Integer semester, @Param("semesterId") Integer semesterId);

    @Query(value="select sw from SchoolSemester sw where sw.yearId = 0 and sw.semesterId <> 0 order by sw.semester asc")
    List<SchoolSemester> findSchoolSemesterNotRank();

    @Query(value="select sw from SchoolSemester sw where sw.yearId = :yearId order by sw.semester asc")
    List<SchoolSemester> findSchoolSemesterByYearId(@Param("yearId") Integer yearId);

    SchoolSemester findSchoolSemesterBySemesterId(Integer semesterId);
}

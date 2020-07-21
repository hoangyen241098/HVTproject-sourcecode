package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SchoolWeekRepository extends JpaRepository<SchoolWeek,Integer> {
    @Query(value = "select s from SchoolWeek s where s.week = :week and s.monthID = :monthId and s.yearId = :yearId")
    SchoolWeek findSchoolWeeksByWeekMonthIdAndYearId(@Param("week") Integer week, @Param("monthId") Integer monthId, @Param("yearId") Integer yearId);

    @Query(value="select sw from SchoolWeek sw where sw.weekID <> 0")
    List<SchoolWeek> findSchoolWeekExcludeZero();

    SchoolWeek findSchoolWeekByWeekID(Integer weekId);
}

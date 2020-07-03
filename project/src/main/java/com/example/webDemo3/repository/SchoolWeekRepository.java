package com.example.webDemo3.repository;

import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchoolWeekRepository extends JpaRepository<SchoolWeek,Integer> {

    @Query(value = "select s from SchoolWeek s where s.yearID = :yearId order by s.fromDate desc")
    List<SchoolWeek> findByYearIdANdSortByFromDate(@Param("yearId") Integer yearId);

}

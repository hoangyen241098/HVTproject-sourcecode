package com.example.webDemo3.repository;

import com.example.webDemo3.entity.ViolationClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface ViolationClassRepository extends JpaRepository<ViolationClass, Long> {

    @Query(value = "select v from ViolationClass v " +
            "where v.classId = :classId " +
            "and v.year.fromYear = :fromYear " +
            "and v.date >= :fromDate " +
            "and v.date <= :toDate " +
            "order by v.date desc")
    List<ViolationClass> findHistoryOfClass(@Param("classId")Integer classId,
                                            @Param("fromYear")Integer fromYear,
                                            @Param("fromDate") Date fromDate,
                                            @Param("toDate")Date toDate);

}

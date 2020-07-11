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

//    List<ViolationClass> findAll

//    @Query(value = "select distinct t.applyDate from TimeTable t order by t.applyDate desc")
//    List<Date> getAllDate();
//
//     @Query(value = "select t from TimeTable t where t.applyDate = :applyDate and t.classId = :classId and (t.isAfternoon = 0 or t.isAfternoon is null) and t.isAdditional = :isAdditional")
//     List<TimeTable> getMorningClassTimeTable(@Param("applyDate") Date applyDate, @Param("classId") Integer classId, @Param("isAdditional") Integer isAdditional);
//
//    @Query(value = "select t from TimeTable t where t.applyDate = :applyDate and t.classId = :classId and t.isAfternoon = 1 and t.isAdditional = :isAdditional")
//    List<TimeTable> getAfternoonClassTimeTable(@Param("applyDate") Date applyDate, @Param("classId") Integer classId, @Param("isAdditional") Integer isAdditional);
//
//    @Query(value = "select t from TimeTable t where t.applyDate = :applyDate and t.teacherId = :teacherId and (t.isAfternoon = 0 or t.isAfternoon is null)")
//    List<TimeTable> getMorningTeacherTimeTable(@Param("applyDate") Date applyDate, @Param("teacherId") Integer teacherId);
//
//    @Query(value = "select t from TimeTable t where t.applyDate = :applyDate and t.teacherId = :teacherId and t.isAfternoon = 1")
//    List<TimeTable> getAfternoonTeacherTimeTable(@Param("applyDate") Date applyDate, @Param("teacherId") Integer teacherId);
}

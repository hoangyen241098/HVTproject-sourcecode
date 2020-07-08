package com.example.webDemo3.repository;

import com.example.webDemo3.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable,Long> {

//    @Query(value = "select MAX(t.weekApply) from TimeTable t where t.weekApply <= :weekApply and t.classId = :classId")
//    Integer getBiggestClosetApplyWeek(@Param("weekApply") Integer weekApply,@Param("classId") Integer classId);
//
//    @Query(value = "select t from TimeTable t where t.weekApply = :weekApply and t.classId = :classId and (t.isAfternoon = 0 or t.isAfternoon is null)")
//    List<TimeTable> getMorningTimeTable(@Param("weekApply") Integer weekApply, @Param("classId") Integer classId);
//
//    @Query(value = "select t from TimeTable t where t.weekApply = :weekApply and t.classId = :classId and t.isAfternoon = 1")
//    List<TimeTable> getAfternoonTimeTable(@Param("weekApply") Integer weekApply, @Param("classId") Integer classId);
//
//    @Query(value = "select MAX(t.weekApply) from TimeTable t where t.weekApply <= :weekApply and t.teacherId = :teacherId")
//    Integer getBiggestClosetApplyWeekTeacher(@Param("weekApply") Integer weekApply,@Param("teacherId") Integer teacherId);
//
//    @Query(value = "select t from TimeTable t where t.weekApply = :weekApply and t.teacherId = :teacherId and (t.isAfternoon = 0 or t.isAfternoon is null)")
//    List<TimeTable> getTeacherMorningTimeTable(@Param("weekApply") Integer weekApply, @Param("teacherId") Integer teacherId);
//
//    @Query(value = "select t from TimeTable t where t.weekApply = :weekApply and t.teacherId = :teacherId and t.isAfternoon = 1")
//    List<TimeTable> getTeacherAfternoonTimeTable(@Param("weekApply") Integer weekApply, @Param("teacherId") Integer teacherId);
}

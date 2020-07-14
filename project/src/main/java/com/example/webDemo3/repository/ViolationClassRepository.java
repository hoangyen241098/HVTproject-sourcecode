package com.example.webDemo3.repository;

import com.example.webDemo3.entity.ViolationClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/*    @Query(value = "select v from ViolationClass v where v.classId = :classId")
    Page<ViolationClass> findByClassId(@Param("classId") Integer classId, Pageable paging);

    @Query(value = "select v from ViolationClass v where v.status = :status")
    Page<ViolationClass> findByStatus(@Param("status") Integer status, Pageable paging);*/

    @Query(value = "select v from ViolationClass v " +
            "where (v.classId = :classId or :classId is NULL ) " +
            "and (v.date = :creatDate or :creatDate is null )" +
            "and v.status = :status ")
    Page<ViolationClass> findViolationClassByConditionAndStatus(@Param("classId")Integer classId,
                                                       @Param("creatDate")Date creatDate,
                                                       @Param("status") Integer status,
                                                       Pageable paging);
/*    @Query(value = "select v from ViolationClass v " +
            "where COALESCE(v.classId,'') = COALESCE(:classId,v.classId,:classIdNull) " +
            "and COALESCE(v.date,'') = COALESCE(:creatDate,v.date,:createDateNull) ")
    Page<ViolationClass> findViolationClassByCondition(@Param("classId")Integer classId,
                                                                @Param("classIdNull")Integer classIdNull,
                                                                @Param("creatDate")Date creatDate,
                                                                @Param("createDateNull")Integer createDateNull,
                                                                Pageable paging);*/

/*
    @Query(value = "select v from ViolationClass v " +
            "where v.classId = coalesce(:classId,v.classId) " +
            "and v.date = coalesce(:creatDate,v.date)")
    Page<ViolationClass> findViolationClassByCondition(@Param("classId")Integer classId,
                                                       @Param("creatDate")Date creatDate,
                                                       Pageable paging);
*/

    @Query(value = "select v from ViolationClass v " +
            "where (v.classId = :classId or :classId is NULL )" +
            "and (v.date = :creatDate or :creatDate is null )")
    Page<ViolationClass> findViolationClassByCondition(@Param("classId")Integer classId,
                                                       @Param("creatDate")Date creatDate,
                                                       Pageable paging);

}

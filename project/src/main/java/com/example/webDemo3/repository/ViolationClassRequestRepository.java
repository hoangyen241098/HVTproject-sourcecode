package com.example.webDemo3.repository;

import com.example.webDemo3.entity.ViolationClassRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface ViolationClassRequestRepository extends JpaRepository<ViolationClassRequest, Integer> {
    //@Query(value = "select v from ViolationClassRequest v where v.classId = :classId and v.status = :status")
    //Page<ViolationClass> findByClassIdAndStatus(@Param("classId") Integer classId, @Param("status") Integer status, Pageable paging);
    @Query(value = "select v from ViolationClassRequest v " +
            "where (v.violationClass.classId = :classId or :classId is NULL ) " +
            "and (v.dateChange = :creatDate or :creatDate is NULL) " +
            "and v.statusChange = :status ")
    Page<ViolationClassRequest> findViolationClassRequestByConditionAndStatus(@Param("classId")Integer classId,
                                                                @Param("creatDate") Date creatDate,
                                                                @Param("status") Integer status,
                                                                Pageable paging);

/*    @Query(value = "select v from ViolationClassRequest v " +
            "where (v.violationClass.classId = :classId or :classId is NULL )" +
            "and (v.dateChange = :creatDate or :creatDate is null)")
    Page<ViolationClassRequest> findViolationClassRequestByCondition(@Param("classId")Integer classId,
                                                                @Param("creatDate") Date creatDate,
                                                                Pageable paging);*/
/*
    @Query(value = "select v from ViolationClassRequest v " +
            "where (v.violationClass.classId = :classId or :classId is NULL )" +
            "and (v.dateChange = :creatDate or :creatDate is null )")
    Page<ViolationClassRequest> findViolationClassRequestByCondition(@Param("classId") Integer classId,
                                                       @Param("creatDate") Date creatDate,
                                                       Pageable paging);*/

    @Query(value = "select t from ViolationClassRequest t where t.dateChange = :creatDate ")
    Page<ViolationClassRequest> findViolationClassRequestByCondition(@Param("creatDate") Date creatDate, Pageable paging);

}

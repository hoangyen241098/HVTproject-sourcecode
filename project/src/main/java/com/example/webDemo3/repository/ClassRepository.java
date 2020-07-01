package com.example.webDemo3.repository;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class,Integer> {
    Class findByClassId(Integer classId);

    Class findByClassIdentifier(String classIdentifier);

    @Query(value = "select c from Class c where c.classIdentifier like %:classIdentifier% and c.grade = :roleId")
    Page<Class> searchClassByCondition(@Param("classIdentifier") String classIdentifier, @Param("roleId") Integer roleId, Pageable paging);

    @Query(value = "select c from Class c where c.classIdentifier like %:classIdentifier%")
    Page<Class> searchClassByClassIdentifier(@Param("classIdentifier") String classIdentifier, Pageable paging);
}

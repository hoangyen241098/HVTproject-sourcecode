package com.example.webDemo3.repository;

import com.example.webDemo3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String>, PagingAndSortingRepository<User, String> {
    User findUserByUsername(String username);

    @Query("select u.username from User u")
    List<String> findAllUsername();

    @Query(value = "select u from User u where u.username like %:username% and u.role.roleId = :roleId")
    Page<User> searchUserByCondition(@Param("username") String username, @Param("roleId") Integer roleId, Pageable paging);

 /*   @Query(value = "select * from USERS u where u.USER_NAME = :username", nativeQuery = true)
    List<User> searchUser(@Param("username") String username);*/

//    @Query(value = "SELECT * FROM Users u WHERE u.status = :status and u.name = :name", nativeQuery = true)
//    User findUserByNamedParamsNative(@Param("status") Integer status, @Param("name") String name);
}
